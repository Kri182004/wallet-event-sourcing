package com.wallet.domain.events;

import java.util.UUID;

public class WalletCreatedEvent {
    private final UUID walletId;

    public WalletCreatedEvent(UUID walletId){
        this.walletId=walletId;
    }
    public UUID getWalletId(){
        return walletId;
    }
}
/* This code says=>>>>>Event = fact
“Wallet was created with this ID”
No logic
No setters
Immutable forever */


//turn wallet into an event sourced aggregate
//in easy terms we are changing how we model the wallet
//from storing balance directly to deriving it from events
//we will create events for wallet creation,credit,debit
//this will help in auditability,traceability and reconstructing state from events
//Event Sourcing is a design pattern where state changes are logged as a sequence of events
//instead of storing just the current state
//each event represents a change that has occurred in the system
//the current state can be reconstructed by replaying these events in order.