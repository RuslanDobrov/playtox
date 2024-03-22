package ru.playtox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import ru.playtox.repository.InMemoryAccountRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAccountRepositoryTest {

    private InMemoryAccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        Map<UUID, Account> accounts = new HashMap<>();
        UUID accountId = UUID.randomUUID();
        accounts.put(accountId, new Account(accountId, BigDecimal.valueOf(1000)));
        accountRepository = new InMemoryAccountRepository(accounts);
    }

    @Test
    public void testFindAccountById_ExistingId_ReturnsAccount() throws NotFoundException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, BigDecimal.valueOf(1000));
        accountRepository.saveAccount(account);
        assertNotNull(accountRepository.findAccountById(accountId));
    }

    @Test
    public void testFindAccountById_NonExistingId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> accountRepository.findAccountById(UUID.randomUUID()));
    }

    @Test
    public void testSaveAccount_AccountSavedSuccessfully() throws NotFoundException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, BigDecimal.valueOf(1000));
        accountRepository.saveAccount(account);
        assertEquals(account, accountRepository.findAccountById(accountId));
    }
}

