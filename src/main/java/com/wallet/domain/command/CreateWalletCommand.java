package com.wallet.domain.command;

import java.util.UUID;
public class CreateWalletCommand {
    private final UUID walletId;
    public CreateWalletCommand(UUID walletId) {
        this.walletId = walletId;
    }
    
    public UUID getWalletId() {
        return walletId;
    }
    
}