package com.wallet.domain;
import java.util.UUID;//Universally Unique Identifier,gives every wallet a unigye globallu id.//much better than init or long for real systems.
public class Wallet {
    private UUID id;//unique identifier for the wallet
    // //no one can chnage it directly
    //protects wallet identity and is a core rule  n good practice
    public Wallet(UUID id){
        this.id=id;//constructor to initialize the wallet with a unique id,when someone creates a wallet they must provide an ID, no wallet can exist without id
    }
    public UUID getId(){
        return id;//getter method to access the wallet's unique id
        //alows read only access to the id,you can see it but not change it
    }
}
