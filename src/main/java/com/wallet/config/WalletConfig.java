package com.wallet.config;

import com.wallet.domain.handler.*;
import com.wallet.domain.query.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletConfig {

    // ---- Repositories / Stores ----

    @Bean
    public InMemoryWalletRepository walletRepository() {
        return new InMemoryWalletRepository();
    }

    @Bean
    public InMemoryTransferStore transferStore() {
        return new InMemoryTransferStore();
    }

    @Bean
    public InMemoryTransferHistoryProjection transferHistoryProjection() {
        return new InMemoryTransferHistoryProjection();
    }

    // ---- Handlers ----

    @Bean
    public WalletCommandHandler walletCommandHandler(
            InMemoryWalletRepository repository
    ) {
        return new WalletCommandHandler(repository);
    }

    @Bean
    public WalletQueryHandler walletQueryHandler(
            InMemoryWalletRepository repository
    ) {
        return new WalletQueryHandler(repository);
    }

    @Bean
    public TransferSaga transferSaga(
            WalletCommandHandler commandHandler,
            InMemoryTransferStore transferStore,
            InMemoryTransferHistoryProjection historyProjection
    ) {
        return new TransferSaga(commandHandler, transferStore, historyProjection);
    }
}
