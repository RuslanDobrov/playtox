package ru.playtox.service;

import lombok.AllArgsConstructor;
import lombok.Locked;
import org.apache.log4j.Logger;
import ru.playtox.MultiThreadedAccountTransfer;
import ru.playtox.exception.InsufficientFundsException;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import ru.playtox.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private static final Logger logger = Logger.getLogger(MultiThreadedAccountTransfer.class);

    @Locked
    @Override
    public void transferFunds(UUID fromAccountId, UUID toAccountId, BigDecimal amount) throws InsufficientFundsException, NotFoundException {
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);
        if (fromAccount.getMoney().compareTo(amount) >= 0) {
            fromAccount.setMoney(fromAccount.getMoney().subtract(amount));
            toAccount.setMoney(toAccount.getMoney().add(amount));
            logger.info("Transfer " + fromAccountId + " -> " + toAccountId + ": " + amount);
        } else {
            logger.error("Insufficient funds in account " + fromAccountId);
            throw new InsufficientFundsException(fromAccountId);
        }
        accountRepository.saveAccount(fromAccount);
        accountRepository.saveAccount(toAccount);
    }
}