package com.wallet.domain.command;

import java.util.UUID;
public class TransferMoneyCommand {
    private final UUID transferId;
    private final UUID fromWalletId;
    private final UUID toWalletId;
    private final long amount;
    
    public TransferMoneyCommand( UUID transferId,UUID fromWalletId, UUID toWalletId, long amount) {
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.transferId=transferId;
    }
    public UUID getTransferId(){
        return transferId;
    }
    public UUID getFromWalletId(){
        return fromWalletId;
    }
    public UUID getToWalletId(){
        return toWalletId;
    }
    public long getAmount(){
        return amount;
    }
}
//WHAT THIS CODE DOES(IN EASY WORDS):
//This code defines a command class called TransferMoneyCommand, which is used to represent the intention
// to transfer a certain amount of money from one wallet to another. The class has three private final fields:
// fromWalletId (of type UUID), toWalletId (of type UUID), and amount (of type long), which are initialized through the constructor.
// The class also provides getter methods for all three fields, allowing other parts of the application to          
// access the source wallet ID, destination wallet ID, and the amount to be transferred when processing this command.
//i am asking : transfer this amount of money from this wallet to that wallet
//no logic , just a data carrier for the command.