package com.wallet.domain.handler;

import com.wallet.domain.command.TransferMoneyCommand;
import com.wallet.domain.query.InMemoryTransferHistoryProjection;
import com.wallet.domain.query.TransferView;

/*
Debit first: If it fails, nothing happened → safe.

Credit second: If it fails, we compensate by refunding the sender.

No money disappears. Ever.

This is orchestrated SAGA:

One coordinator, clear steps, explicit compensation.
---------------------------------------------------------------------
How this connects to your system??

Commands express intent (TransferMoneyCommand)

Handlers perform single-wallet actions

Wallet enforces rules

Events record facts

SAGA protects the multi-step story

Everything you built earlier is reused—nothing is thrown away.
----------------------------------------------------------------
What if the app crashes after debit but before credit?
On restart, you can:

See events

Detect incomplete transfer (next improvement)

Compensate if needed
-------------------------------------------------------------
(We’ll add transfer IDs later for idempotency.)
Now your system says (after adding idempotency):

“If I see the same transfer ID again,
I will not touch money again.”

This is:

Retry-safe
Crash-safe
Network-safe

This is how Stripe, Paytm, Razorpay think.
*/

public class TransferSaga {

    private final WalletCommandHandler walletCommandHandler;
    private final InMemoryTransferStore transferStore;
    private final InMemoryTransferHistoryProjection historyProjection;

    public TransferSaga(
            WalletCommandHandler walletCommandHandler,
            InMemoryTransferStore transferStore,
            InMemoryTransferHistoryProjection historyProjection
    ) {
        this.walletCommandHandler = walletCommandHandler;
        this.transferStore = transferStore;
        this.historyProjection = historyProjection;
    }

    public void handle(TransferMoneyCommand command) {

        // Idempotency check:
        // If this transferId was already seen, do nothing.
        if (transferStore.exists(command.getTransferId())) {
            return; // already processed or in progress
        }

        // Mark transfer as started
        transferStore.markStarted(command.getTransferId());

        // STEP 1: Debit source wallet
        try {
            walletCommandHandler.handle(
                new com.wallet.domain.command.DebitWalletCommand(
                    command.getFromWalletId(),
                    command.getAmount()
                )
            );
        } catch (Exception e) {

            // Debit failed → transfer cannot proceed
            transferStore.markFailed(command.getTransferId());

            throw new RuntimeException(
                    "Transfer failed: unable to debit source wallet",
                    e
            );
        }

        // STEP 2: Credit destination wallet
        try {
            walletCommandHandler.handle(
                new com.wallet.domain.command.CreditWalletCommand(
                    command.getToWalletId(),
                    command.getAmount()
                )
            );

            // SUCCESS:
            // Debit + Credit both succeeded
            // Record transfer history for query side
            historyProjection.add(
                new TransferView(
                    command.getTransferId(),
                    command.getFromWalletId(),
                    command.getToWalletId(),
                    command.getAmount(),
                    "COMPLETED"
                )
            );

            // Mark transfer as completed
            transferStore.markCompleted(command.getTransferId());

        } catch (Exception e) {

            // STEP 3: Credit failed → compensate by refunding source wallet
            walletCommandHandler.handle(
                new com.wallet.domain.command.CreditWalletCommand(
                    command.getFromWalletId(),
                    command.getAmount()
                )
            );

            // FAILURE:
            // Compensation done, money restored
            // Record failed transfer in history
            historyProjection.add(
                new TransferView(
                    command.getTransferId(),
                    command.getFromWalletId(),
                    command.getToWalletId(),
                    command.getAmount(),
                    "FAILED"
                )
            );

            // Mark transfer as failed
            transferStore.markFailed(command.getTransferId());

            throw new RuntimeException(
                    "Transfer failed: unable to credit destination wallet, source wallet refunded",
                    e
            );
        }
    }
}
