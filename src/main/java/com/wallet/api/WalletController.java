package com.wallet.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.domain.command.CreateWalletCommand;
import com.wallet.domain.command.CreditWalletCommand;
import com.wallet.domain.command.TransferMoneyCommand;
import com.wallet.domain.handler.TransferSaga;
import com.wallet.domain.handler.WalletCommandHandler;
import com.wallet.domain.query.GetWalletBalanceQuery;
import com.wallet.domain.query.InMemoryTransferHistoryProjection;
import com.wallet.domain.query.TransferView;
import com.wallet.domain.query.WalletBalanceView;
import com.wallet.domain.query.WalletQueryHandler;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletCommandHandler walletCommandHandler;
    private final WalletQueryHandler walletQueryHandler;
    private final TransferSaga transferSaga;
    private final InMemoryTransferHistoryProjection historyProjection;

    public WalletController(WalletCommandHandler walletCommandHandler, WalletQueryHandler walletQueryHandler, TransferSaga transferSaga, InMemoryTransferHistoryProjection historyProjection) {
        this.walletCommandHandler = walletCommandHandler;
        this.walletQueryHandler = walletQueryHandler;
        this.transferSaga = transferSaga;
        this.historyProjection = historyProjection;
    }
   @PostMapping
public void createwallet(@RequestParam UUID walletId) {
    walletCommandHandler.handle(new CreateWalletCommand(walletId));
}


    @PostMapping("/transfer")
        public void transfer(@RequestParam UUID from,
            @RequestParam UUID to,
            @RequestParam long amount
        ){
            transferSaga.handle(new TransferMoneyCommand(
                UUID.randomUUID(),from, to, amount));
        }
    @GetMapping("{id}/balance")
    public WalletBalanceView getBalance(@PathVariable UUID id){
        return walletQueryHandler.handle(new GetWalletBalanceQuery(id));
    }
    @GetMapping("{id}/transfers")
    public List<TransferView> getTransfer(@PathVariable UUID id){
        return historyProjection.findWalletId(id);
}
@PostMapping("/{id}/credit")
public void credit(@PathVariable UUID id,
                   @RequestParam long amount) {
    walletCommandHandler.handle(
        new CreditWalletCommand(id, amount)
    );
}

}