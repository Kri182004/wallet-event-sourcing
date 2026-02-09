package com.wallet.domain.query;

import java.util.UUID;

import com.wallet.domain.Wallet;
import com.wallet.domain.handler.InMemoryWalletRepository;
public class WalletQueryHandler {
    private final InMemoryWalletRepository repository;
    public  WalletQueryHandler(InMemoryWalletRepository repository){
        this.repository=repository;
    }   
    public WalletBalanceView handle(GetWalletBalanceQuery query){
        Wallet wallet =repository.load(query.getWalletId());
        return new WalletBalanceView(
            wallet.getId(),
             wallet.getBalance());
    }
}
//meaning
//reads state
//doesnt change state anything
//return a safe snapshot of the state of the wallet, it is a read model, it is a projection of the state of the wallet, it is a view of the state of the wallet, it is a DTO that carries the data of the wallet balance, it is a query handler that handles the query GetWalletBalanceQuery and returns the WalletBalanceView./
/*Write Side:
Command → Handler → Wallet → Events

Read Side:
Query → QueryHandler → Projection 
 both sides are separate, they can be scaled independently, they can be optimized for their specific use cases, they can have different data models, they can have different databases, they can have different APIs, they can have different teams working on them,
 they never share code, they never share data, they never share models, they never share databases, they never share APIs, they never share teams, they are completely independent, they are completely decoupled,
this is TRUE CQRS*/