package ru.playtox.repository;

import lombok.AllArgsConstructor;
import ru.playtox.exception.NotFoundException;
import ru.playtox.model.Account;
import java.math.BigDecimal;
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
//        сheckAccountBalances();
    }

//    private void сheckAccountBalances() {
//        BigDecimal totalBalance = BigDecimal.ZERO;
//        for (Account account : accounts.values()) {
//            System.out.println("Account: " + account.getId() + " has balance: " + account.getMoney());
//            totalBalance = totalBalance.add(account.getMoney());
//        }
//
//        System.out.println("Total balance on all accounts: " + totalBalance);
//        System.out.println("Expected balance: 40000");
//    }
}
