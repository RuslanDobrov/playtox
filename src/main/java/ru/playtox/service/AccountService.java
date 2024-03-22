package ru.playtox.service;

public interface AccountService {
    void transferFunds(int fromAccountId, int toAccountId, int amount);
}
