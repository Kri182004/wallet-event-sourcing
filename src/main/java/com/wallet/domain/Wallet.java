package com.wallet.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;//Universally Unique Identifier,gives every wallet a unigye globallu id.//much better than init or long for real systems.

import com.wallet.domain.events.WalletCreatedEvent;
public class Wallet {
    private  UUID id;//unique identifier for the wallet,// final means once assigned it cannot be changed
    // //no one can chnage it directly
    //protects wallet identity and is a core rule  n good practice
    private long balance;
    private final List<Object> uncommittedEvents=new ArrayList<>(); 
    private Wallet(){//private constructor to force use of factory method,
//ensures wallet is always created with a creation event,you are not allowed to create a walllet directly.
    }
    public static Wallet create(UUID walletId) {
        Wallet wallet = new Wallet();
        wallet.apply(new WalletCreatedEvent(walletId));//apply method handles the event and updates the state accordingly
        return wallet;
    }

    public UUID getId(){
        return id;//getter method to access the wallet's unique id
        //alows read only access to the id,you can see it but not change it
    }
    public long getBalance(){
        return balance;
    }
    public List<Object> getUncommittedEvents(){
        return uncommittedEvents;
    }
    public void apply(Object event){
        /*This method does two things:Mutates state (id, balance),Records the event for persistence
Important:The event comes first,State follows*/
        if(event instanceof WalletCreatedEvent e){
            this.id=e.getWalletId();
            this.balance=0;
            //handle wallet created event
            //no state change needed here since wallet is already created in constructor
    }
    uncommittedEvents.add(event);//record the event for persistence
    //new event is added to the list of uncommitted events
    //these events can later be persisted to an event store
    //this ensures all changes to the wallet are tracked via events
    //this is the essence of event sourcing,things that just happened are recorded as events
}
}
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