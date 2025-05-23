package org.galax1y.repositories;

import org.galax1y.entities.Account;

import java.util.*;

public class InMemoryAccountRepository implements IAccountRepository {
    private final Map<UUID, Account> items = new HashMap<>();

    @Override
    public Optional<Account> getById(UUID accountId) {
        return Optional.ofNullable(items.get(accountId));
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return items.values()
                .stream()
                .filter(account -> email.equals(account.getEmail()))
                .findFirst();
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Account create(Account account) {
        items.put(account.getId(), account);
        return account;
    }

    @Override
    public Account update(Account account) {
        items.put(account.getId(), account);
        return account;
    }

    @Override
    public Account delete(UUID accountId) {
        return items.remove(accountId);
    }

    @Override
    public boolean exists(UUID accountId) {
        return items.containsKey(accountId);
    }
}