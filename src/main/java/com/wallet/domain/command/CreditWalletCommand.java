package com.wallet.domain.command;

import java.util.UUID;
public class CreditWalletCommand {
    private final UUID walletId;
    private final long amount;

    public CreditWalletCommand(UUID walletId, long amount) {
        this.walletId = walletId;
        this.amount = amount;
    }
public UUID getwalletId(){
    return walletId;
}
public long getAmount(){
    return amount;

}
}
//WHAT THIS CODE DOES(IN EASY WORDS):
//This code defines a command class called CreditWalletCommand,//
//  which is used to represent the intention to credit a certain amount of money to a wallet .//
// identified by its unique ID (walletId). The class has two private final fields: //
// walletId (of type UUID) and amount (of type long), which are initialized through the constructor. 
//The class also provides getter methods for both fields, allowing other parts of the application to access the wallet ID and the amount to be credited when processing this command.