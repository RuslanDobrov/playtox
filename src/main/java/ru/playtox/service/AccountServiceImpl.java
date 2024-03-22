package ru.playtox.service;

import ru.playtox.model.Account;
import ru.playtox.repository.AccountRepository;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private Lock lock = new ReentrantLock();
    private Logger logger;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.logger = Logger.getLogger("AccountService");
    }
    @Override
    public void transferFunds(int fromAccountId, int toAccountId, int amount) {
        lock.lock();
        try {
            Account fromAccount = accountRepository.findAccountById(fromAccountId);
            Account toAccount = accountRepository.findAccountById(toAccountId);

            if (fromAccount == null || toAccount == null) {
                logger.info("Invalid account IDs");
                return;
            }

            if (fromAccount.getMoney() < amount) {
                logger.info("Insufficient funds in account " + fromAccountId);
                return;
            }

            fromAccount.setMoney(fromAccount.getMoney() - amount);
            toAccount.setMoney(toAccount.getMoney() + amount);

            accountRepository.saveAccount(fromAccount);
            accountRepository.saveAccount(toAccount);
        } finally {
            lock.unlock();
        }
    }
}