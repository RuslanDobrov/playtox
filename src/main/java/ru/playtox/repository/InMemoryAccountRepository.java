package ru.playtox.repository;

import lombok.AllArgsConstructor;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class InMemoryAccountRepository implements AccountRepository {

    private Map<UUID, Account> accounts;

    @Override
    public Account findAccountById(UUID id) throws NotFoundException {
        if (accounts.containsKey(id)) {
            return accounts.get(id);
        } else {
            throw new NotFoundException(id);
        }
    }

    @Override
    public void saveAccount(Account account) {
        accounts.put(account.getId(), account);
    }
}
