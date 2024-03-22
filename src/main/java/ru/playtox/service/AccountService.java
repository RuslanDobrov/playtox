package ru.playtox.service;

import ru.playtox.exception.InsufficientFundsException;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    void transferFunds(UUID fromAccountId, UUID toAccountId, BigDecimal amount) throws InsufficientFundsException, NotFoundException;
}
