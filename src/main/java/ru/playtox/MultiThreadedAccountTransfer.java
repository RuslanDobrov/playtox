package ru.playtox;

import org.apache.log4j.Logger;
import ru.playtox.config.AppConfig;
import ru.playtox.exception.InsufficientFundsException;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import ru.playtox.repository.AccountRepository;
import ru.playtox.repository.InMemoryAccountRepository;
import ru.playtox.service.AccountService;
import ru.playtox.service.AccountServiceImpl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MultiThreadedAccountTransfer {
    private static final Logger logger = Logger.getLogger(MultiThreadedAccountTransfer.class);
    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();
        Map<UUID, Account> accounts = new HashMap<>();

        for (int i = 0; i < appConfig.getNumberOfAccounts(); i++) {
            UUID id = UUID.randomUUID();
            accounts.put(id, new Account(id, appConfig.getInitialFundsBalance()));
        }

        AccountRepository accountRepository = new InMemoryAccountRepository(accounts);
        AccountService accountService = new AccountServiceImpl(accountRepository);
        Random random = new Random();

        for (int i = 0; i < appConfig.getNumberOfThreads(); i++) {
            new Thread(() -> {
               try {
                    for (int j = 0; j < appConfig.getNumberOfTransactions(); j++) {
                        UUID fromAccountId = getRandomAccountId(accounts);
                        UUID toAccountId = getRandomAccountId(accounts);
                        BigDecimal amount = BigDecimal.valueOf(random.nextInt(1000));
                        accountService.transferFunds(fromAccountId, toAccountId, amount);
                        Thread.sleep(random.nextInt(1001) + 1000);
                    }
                } catch (InterruptedException e) {
                   logger.error("Thread interrupted", e);
                   e.printStackTrace();
                } catch (InsufficientFundsException e) {
                   logger.error("Insufficient funds", e);
                   e.printStackTrace();
                } catch (NotFoundException e) {
                   logger.error("Insufficient funds", e);
                   e.printStackTrace();
               }
            }).start();
        }
    }

    private static UUID getRandomAccountId(Map<UUID, Account> accounts) {
        int index = new Random().nextInt(accounts.size());
        return (UUID) accounts.keySet().toArray()[index];
    }
}