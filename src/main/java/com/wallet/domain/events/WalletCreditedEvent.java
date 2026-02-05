package com.wallet.domain.events;

import java.util.UUID;

public class WalletCreditedEvent {

    private final UUID walletId;
    private final long amount;

    public WalletCreditedEvent(UUID walletId, long amount) {
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

//all the code above literally says =>This wallet received this much amount
//event is immutable,no setters,no logic,just data
//NOW UPDATE WALLET TO HANDLE THIS EVENT
