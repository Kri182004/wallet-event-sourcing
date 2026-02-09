package com.wallet.domain.handler;

import com.wallet.domain.Wallet;
import com.wallet.domain.command.*;

public class WalletCommandHandler {

    private final InMemoryWalletRepository repository;

    public WalletCommandHandler(InMemoryWalletRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateWalletCommand command) {
        Wallet wallet = Wallet.create(command.getWalletId());
        repository.save(wallet);
    }

    public void handle(CreditWalletCommand command) {
        Wallet wallet = repository.load(command.getWalletId());
        wallet.credit(command.getAmount());
        repository.save(wallet);
    }

    public void handle(DebitWalletCommand command) {
        Wallet wallet = repository.load(command.getWalletId());
        wallet.debit(command.getAmount());
        repository.save(wallet);
    }
}

//WHAT THIS CODE DOES(IN EASY WORDS):
//This code defines a command handler class called WalletCommandHandler,
// which is responsible for processing commands related to wallet operations such as creating a wallet, crediting a wallet, and debiting a wallet.
// The handler interacts with an InMemoryWalletRepository to load and save wallet instances. //
// Each handle method corresponds to a specific command type and performs the necessary operations on the wallet before saving the updated state back to the repository.
//
// For example, when handling a CreateWalletCommand, it creates a new wallet using the factory method and saves it to the repository. //
// When handling a CreditWalletCommand, it loads the existing wallet, performs the credit operation, and then saves the updated wallet back to the repository. //
// Similarly, when handling a DebitWalletCommand, it loads the wallet, performs the debit operation, and saves the updated wallet back to the repository.
//Overall, this class serves as the central point for processing wallet-related commands and managing the state of wallets through the repository.

/*Now your project has clear CQRS:

✔ Command side (write)

Commands → intent

Handler → orchestration

Wallet → business rules

Events → facts */