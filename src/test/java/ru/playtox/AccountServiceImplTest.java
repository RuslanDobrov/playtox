package ru.playtox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.playtox.exception.InsufficientFundsException;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import ru.playtox.repository.AccountRepository;
import ru.playtox.service.AccountServiceImpl;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferFunds_WithSufficientBalance_TransfersSuccessfully() throws InsufficientFundsException, NotFoundException {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        Account fromAccount = new Account(fromAccountId, BigDecimal.valueOf(500));
        Account toAccount = new Account(toAccountId, BigDecimal.valueOf(0));
        BigDecimal amountToTransfer = BigDecimal.valueOf(500);

        when(accountRepository.findAccountById(fromAccountId)).thenReturn(fromAccount);
        when(accountRepository.findAccountById(toAccountId)).thenReturn(toAccount);

        accountService.transferFunds(fromAccountId, toAccountId, amountToTransfer);

        assertEquals(BigDecimal.valueOf(0), fromAccount.getMoney());
        assertEquals(BigDecimal.valueOf(500), toAccount.getMoney());
        verify(accountRepository, times(1)).saveAccount(fromAccount);
        verify(accountRepository, times(1)).saveAccount(toAccount);
    }

    @Test
    public void testTransferFunds_WithInsufficientBalance_ThrowsInsufficientFundsException() throws NotFoundException {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        Account fromAccount = new Account(fromAccountId, BigDecimal.valueOf(100));
        Account toAccount = new Account(toAccountId, BigDecimal.valueOf(0));
        BigDecimal amountToTransfer = BigDecimal.valueOf(101);

        when(accountRepository.findAccountById(fromAccountId)).thenReturn(fromAccount);
        when(accountRepository.findAccountById(toAccountId)).thenReturn(toAccount);

        assertThrows(InsufficientFundsException.class, () -> accountService.transferFunds(fromAccountId, toAccountId, amountToTransfer));
        assertEquals(BigDecimal.valueOf(100), fromAccount.getMoney());
        assertEquals(BigDecimal.valueOf(0), toAccount.getMoney());
        verify(accountRepository, never()).saveAccount(any());
    }
}

