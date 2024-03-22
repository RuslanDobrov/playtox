package ru.playtox.repository;

import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import java.util.UUID;

public interface AccountRepository {
    Account findAccountById(UUID id) throws NotFoundException;
    void saveAccount(Account account);
}
