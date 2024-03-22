package ru.playtox;

import ru.playtox.model.Account;

import ru.playtox.repository.AccountRepository;
import ru.playtox.repository.InMemoryAccountRepository;
import ru.playtox.service.AccountService;
import ru.playtox.service.AccountServiceImpl;

import java.util.Random;

public class MultiThreadedAccountTransfer {
    public static void main(String[] args) {
        Account[] accounts = new Account[4];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, 10000);
        }

        AccountRepository accountRepository = new InMemoryAccountRepository(accounts);
        AccountService accountService = new AccountServiceImpl(accountRepository);

        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println("thread start");
                for (int j = 0; j < 3; j++) {
                    System.out.println("transfer " + j + " start");
                    int fromAccountId = random.nextInt(accounts.length);
                    int toAccountId = random.nextInt(accounts.length);
                    int amount = random.nextInt(1000);
                    accountService.transferFunds(fromAccountId, toAccountId, amount);
                    try {
                        System.out.println("thread sleep");
                        Thread.sleep(random.nextInt(1001) + 1000);
                        System.out.println("thread vaike up");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}