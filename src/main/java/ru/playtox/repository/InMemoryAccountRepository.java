package ru.playtox.repository;

import ru.playtox.model.Account;

public class InMemoryAccountRepository implements AccountRepository {
    private Account[] accounts;

    public InMemoryAccountRepository(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public Account findAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }
    @Override
    public void saveAccount(Account account) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getId() == account.getId()) {
                accounts[i] = account;
                break;
            }
        }
    }
}
