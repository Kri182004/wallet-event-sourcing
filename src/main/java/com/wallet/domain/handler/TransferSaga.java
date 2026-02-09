package com.wallet.domain.handler;

import com.wallet.domain.command.TransferMoneyCommand;

public class TransferSaga {
    private final WalletCommandHandler walletCommandHandler;
        private final InMemoryTransferStore transferStore;

    public TransferSaga(WalletCommandHandler walletCommandHandler, InMemoryTransferStore transferStore) {
        this.walletCommandHandler = walletCommandHandler;
        this.transferStore = transferStore;
    }
    public void handle(TransferMoneyCommand command){
        //idempotency check (not implemented here, but you would check transferStore for existing transferId)
        if(transferStore.exists(command.getTransferId())){
            return; //already processing or completed
        }
        transferStore.markStarted(command.getTransferId());
        //1. Debit source wallet
        try{
        walletCommandHandler.handle(
            new com.wallet.domain.command.DebitWalletCommand(
                command.getFromWalletId(),
                command.getAmount()
            )

        );
    }catch(Exception e){
        //2.debit failed → stop saga/transfer
        throw new RuntimeException("Transfer failed: unable to debit source wallet",e);
        
    }
    //step 2: credit destination wallet
    try{
        walletCommandHandler.handle(
            new com.wallet.domain.command.CreditWalletCommand(
                command.getToWalletId(),
                command.getAmount()
            )
        );
    }catch(Exception e){
        //3. credit failed → compensate by refunding source wallet
        walletCommandHandler.handle(
            new com.wallet.domain.command.CreditWalletCommand(
                command.getFromWalletId(),
                command.getAmount()
            )
        );
        throw new RuntimeException("Transfer failed: unable to credit destination wallet, source wallet refunded",e);
    }
        
    }
}


/*Debit first: If it fails, nothing happened → safe.

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
(We’ll add transfer IDs later for idempotency.)*/
/*Now your system says:(after adding idempotency)

“If I see the same transfer ID again,
I will not touch money again.”

This is:

Retry-safe

Crash-safe

Network-safe

This is how Stripe, Paytm, Razorpay think. */
