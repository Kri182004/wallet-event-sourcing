package com.wallet.domain.command;
import java.util.UUID;
public class DebitWalletCommand {
    private final UUID walletId;
    private final long amount;;

    public DebitWalletCommand(UUID walletId, long amount) {
        this.walletId = walletId;
        this.amount = amount;
    }
public UUID getWalletId(){
    return walletId;
}
public long getAmount(){
    return amount;
}
}
