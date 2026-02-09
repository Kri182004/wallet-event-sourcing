package com.wallet.domain.handler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTransferStore {
    private final Map<UUID,TransferStatus> transfers= new ConcurrentHashMap<>();
     
    public boolean exists(UUID transferId){
        return transfers.containsKey(transferId);
    }
    public void markStarted(UUID transferId){
        transfers.put(transferId,TransferStatus.STARTED);
    }
    public void markCompleted(UUID transferId){
        transfers.put(transferId,TransferStatus.COMPLETED);
    }
    public void markFailed(UUID transferId){
        transfers.put(transferId,TransferStatus.FAILED);
    }
    
}
//  WHAT THIS CODE DOES(IN EASY WORDS)
// This code defines an in-memory store for tracking the status of money transfers.
//  It uses a ConcurrentHashMap to map transfer IDs (UUIDs) to their current status (TransferStatus). 
// The class provides methods to check if a transfer exists, create a new transfer with a "STARTED" status, and update the status of an existing transfer to "COMPLETED" or "FAILED". 
// This allows the application to keep track of the state of each transfer as it progresses through the system.: