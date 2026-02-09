package com.wallet.domain.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.wallet.domain.Wallet;

import java.util.List;
public class InMemoryWalletRepository {

    // In-memory event store: maps wallet ID to list of events
    private final Map<UUID,List<Object>> eventStore= new HashMap<>();
    
    public void save(Wallet wallet){
        eventStore.put(wallet.getId(),wallet.getUncommittedEvents());
        // In a real implementation, you would persist these events to a database or event store
        // For this in-memory example, we simply store them in a map. After saving, you would typically clear the uncommitted events from the wallet.
        // After saving, clear uncommitted events
  
    }
    public Wallet load(UUID walletId){
        List<Object> events= eventStore.get(walletId); 
        if (events == null) {
            throw new IllegalArgumentException("Wallet not found");
        }
        return Wallet.replay(events);
    }
}
//WHAT THIS CODE DOES(IN EASY WORDS):
//in simple words, this code defines an in-memory repository(means it stores data in memory, not in a database means it will be lost when the application stops) for managing Wallet objects in an event-sourced system.
// //The repository provides methods to save a wallet's uncommitted events and to load a wallet by replaying its stored events. It uses a HashMap to store the events associated with each wallet ID,//
//  allowing you to retrieve and reconstruct the state of a wallet based on its event history.
//Note: The actual implementation of the Wallet.replay method is not shown here, but it would be responsible for applying the list of events to reconstruct the wallet's state.
//In a real application, you would typically have more robust error handling and persistence mechanisms, but this example serves to illustrate the basic concept of an in-memory event store for a wallet domain.

//why we need to clear uncommitted events after saving?
//We need to clear uncommitted events after saving because once the events have been persisted to the event store, they are considered "committed" and should not be stored again as uncommitted events.
//If we do not clear the uncommitted events, the next time we save the wallet, it would attempt to save the same events again, leading to duplicate entries in the event store. This can cause issues when replaying events to reconstruct the wallet's state, as it may apply the same events multiple times, resulting in an incorrect state of the wallet.
//By clearing the uncommitted events after saving, we ensure that only new events that have occurred since the last save are stored as uncommitted events, and we avoid any potential duplication or inconsistencies in the event store.
//In summary, clearing uncommitted events after saving is essential to maintain the integrity of the event store and to ensure that the wallet's state is accurately reconstructed when replaying events.

//why we use in-memory repository?
//We use an in-memory repository for several reasons, especially in the context of development, testing, and simple applications:
//1. Simplicity: An in-memory repository is straightforward to implement and use. It allows developers to focus on the core logic of their application without worrying about the complexities of database connections, transactions, and persistence mechanisms.
//2. Speed: In-memory repositories are typically faster than database-backed repositories because they operate directly in memory, eliminating the overhead associated with disk I/O and network communication.
//3. Testing: In-memory repositories are ideal for unit testing and integration testing because they provide a lightweight and isolated environment. They allow developers to test the behavior of their application without relying on external dependencies like databases, which can be slower and more complex to set up.
//4. Prototyping: For quick prototyping and experimentation, an in-memory repository can be a convenient choice. It allows developers to rapidly iterate on their design and functionality without the need for a fully-fledged database.
//5. Learning and Demonstration: In-memory repositories are often used in educational contexts to demonstrate concepts like event sourcing, domain-driven design, and repository patterns without the added complexity of database management.
//However, it's important to note that in-memory repositories are not suitable for production use, as they do not provide durability or scalability. In a production environment, you would typically use a persistent storage solution, such as a relational database, NoSQL database, or an event store, to ensure that your data is safely stored and can be retrieved even after application restarts or failures.