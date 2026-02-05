package com.wallet.domain;

import com.wallet.domain.events.WalletCreatedEvent;
import com.wallet.domain.events.WalletCreditedEvent;
import com.wallet.domain.events.WalletDebitedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {

    private UUID id;
    private long balance;

    // stores events that occurred but are not yet persisted
    private final List<Object> uncommittedEvents = new ArrayList<>();

    // private constructor → forces event-based creation
    private Wallet() {
    }

    // factory method to create wallet via event
    public static Wallet create(UUID walletId) {
        Wallet wallet = new Wallet();
        wallet.apply(new WalletCreatedEvent(walletId));
        return wallet;
    }

    // getters (read-only access)
    public UUID getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Object> getUncommittedEvents() {
        return uncommittedEvents;
    }

    // ===== DOMAIN BEHAVIOR =====

    public void credit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        apply(new WalletCreditedEvent(this.id, amount));
    }
    public void debit(long amount){
        if(amount<=0){
            throw new IllegalArgumentException("Debit amount must be positive");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient balance");            
        }
        apply(new WalletDebitedEvent(this.id, amount));
    }
    // ===== EVENT APPLICATION =====
    // the ONLY place where state is mutated
    private void apply(Object event) {

        if (event instanceof WalletCreatedEvent e) {
            this.id = e.getWalletId();
            this.balance = 0;
        }

        if (event instanceof WalletCreditedEvent e) {
            this.balance += e.getAmount();
        }

        if (event instanceof WalletDebitedEvent e) {
            this.balance -= e.getAmount();
        }

        // record every applied event
        uncommittedEvents.add(event);
    }
}
/*1️ Wallet is created only through an event, so every wallet has a recorded history of how it started.
2️ Money is added by creating a “credit” event, not by changing the balance directly.
3️ All changes to balance happen inside apply(), which reads events and updates state.
4️ Events are stored in uncommittedEvents, so they can be saved or replayed later.
5️ Balance is not the truth — events are, and balance is just calculated from them. */

/*wallet.credit(100);
wallet.debit(40);
Final balance: 60
We only know the final balance
balance is stored directly
When the app stops → state is gone
We only know the final balance
We don’t know how we reached it
this is called state based model
--->
Now consider event based model
Events:
1. WalletCreated
2. MoneyCredited
3. MoneyDebited
Final balance: 60
We know all the events that led to this state
balance is derived from events,not stored directly,which is better for audit and traceability
When the app stops → we can reconstruct state from events,not lost
We know how we reached the final balance.
*/