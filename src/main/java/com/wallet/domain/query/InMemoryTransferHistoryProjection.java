package com.wallet.domain.query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryTransferHistoryProjection {

    // This class is an in-memory projection that maintains a list of transfer views representing the transfer history.
    private final List<TransferView> transferHistory = new ArrayList<>();
   // The add method allows adding a new TransferView to the transfer history list.
    public void add(TransferView view){
        transferHistory.add(view);
    }
    // The findWalletId method takes a walletId as input and returns a list of TransferView objects that are associated with that walletId, either as the sender (fromWalletId) or the receiver (toWalletId).
    public List<TransferView> findWalletId(UUID walletId){
        List<TransferView> result = new ArrayList<>();
        for(TransferView t : transferHistory){
            if(t.getFromWalletId().equals(walletId) || t.getToWalletId().equals(walletId)){
                result.add(t);
            }
        }
        return result;
    }
}
//so basically yeh class ek in-memory projection hai jo transfer history ko maintain karta hai. 
// Isme ek list hoti hai jisme TransferView objects store hote hain, jo har transfer ke details ko represent karte hain. 
// add method se hum naye transfers ko is list me add kar sakte hain, aur findWalletId method se hum kisi specific walletId ke associated transfers ko retrieve kar sakte hain.
//toh overall, yeh class transfer history ko manage karne ke liye use hoti hai, aur isme hum easily kisi wallet ke transfers ko dekh sakte hain.
