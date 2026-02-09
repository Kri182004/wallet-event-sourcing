package com.wallet.domain;

import com.wallet.domain.events.WalletCreatedEvent;
import com.wallet.domain.events.WalletCreditedEvent;
import com.wallet.domain.events.WalletDebitedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {

    @Test
    void replay_should_rebuild_correct_balance() {
        UUID walletId = UUID.randomUUID();

        // given: wallet created and used normally
        Wallet wallet = Wallet.create(walletId);
        wallet.credit(100);
        wallet.debit(40);

        // when: we replay the events
        List<Object> events = wallet.getUncommittedEvents();
        Wallet replayedWallet = Wallet.replay(events);

        // then: state is rebuilt correctly
        assertEquals(60, replayedWallet.getBalance());
        assertEquals(walletId, replayedWallet.getId());
    }
}
