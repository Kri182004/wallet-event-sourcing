package com.wallet.domain.query;

import java.util.UUID;
public class GetWalletBalanceQuery {
    private final UUID walletId;
    public GetWalletBalanceQuery(UUID walletId){
        this.walletId=walletId;
    }
    public UUID getWalletId(){
        return walletId;
    }
}

//WHAT THIS CODE DOES(IN EASY WORDS):
//This code defines a query/question class called GetWalletBalanceQuery, 
// which is used to represent the intention to retrieve the balance of a wallet identified by its unique ID (walletId). 
// The class has a private final field walletId (of type UUID), which is initialized through the constructor. 
// The class also provides a getter method getwalletId() that allows other parts of the application to access the wallet ID when processing this query.
//i am asking :what is the balance of the wallet with this id?
//no logic , just a data carrier for the query.
