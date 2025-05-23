package org.galax1y.repositories;

import org.galax1y.entities.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountRepository {
    Optional<Account> getById(UUID accountId);
    Optional<Account> getByEmail(String email);
    List<Account> getAll();
    Account create(Account account);
    Account update(Account account);
    Account delete(UUID accountId);
    boolean exists(UUID accountId);
}
