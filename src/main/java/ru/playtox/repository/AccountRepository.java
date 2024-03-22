package ru.playtox.repository;

import ru.playtox.model.Account;

public interface AccountRepository {
    Account findAccountById(int id);
    void saveAccount(Account account);
}
