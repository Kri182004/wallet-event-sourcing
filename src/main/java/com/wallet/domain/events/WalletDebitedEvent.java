package com.wallet.domain.events;

import java.util.UUID;;
public class WalletDebitedEvent {
    private final UUID walletId;
    private final long amount;

    public WalletDebitedEvent(UUID walletId, long amount) {
        this.walletId = walletId;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public long getAmount() {
        return amount;
    }
}
//all the code above literally says =>This wallet lost this much amount