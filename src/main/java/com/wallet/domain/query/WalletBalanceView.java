package com.wallet.domain.query;
import java.util.UUID;
public class WalletBalanceView {
    private final UUID walletId;
    private final long balance;
 public WalletBalanceView(UUID walletId,long balance){
    this.walletId=walletId;
    this.balance=balance;
 }
 public UUID getWalletId(){
    return walletId;
 }
 public long getBalance(){
    return balance;
 }
}

//WHAT THIS CODE DOES(IN EASY WORDS):
//This code defines a simple data class called WalletBalanceView, 
// which is used to represent the balance of a wallet with a specific ID.
//this shows the result of the query GetWalletBalanceQuery, it carries the walletId and the balance of that wallet. 