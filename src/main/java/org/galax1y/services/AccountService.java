package org.galax1y.services;

import org.galax1y.entities.Account;
import org.galax1y.errors.ConflictException;
import org.galax1y.errors.EntityNotFoundException;
import org.galax1y.repositories.IAccountRepository;

import java.util.Optional;
import java.util.UUID;

interface IAccountService {
    Account createAccount(String name, String email, String password);
    Account updateAccount(Account account);
    Account removeAccount(UUID accountId);
}

public class AccountService implements IAccountService {
    private final IAccountRepository _accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this._accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String name, String email, String password) {
        if (_accountRepository.getByEmail(email).isPresent()) {
            throw new ConflictException("Account", "email");
        }

        return _accountRepository.create(Account.create(name, email, password));
    }

    @Override
    public Account updateAccount(Account account) {
        UUID accountId = account.getId();

        if (!_accountRepository.exists(accountId)) {
            throw new EntityNotFoundException("Account", accountId);
        }

        Optional<Account> accountWithSameEmail = _accountRepository.getByEmail(account.getEmail());

        if (accountWithSameEmail.isPresent() && accountWithSameEmail.get().getId() != accountId) {
            throw new ConflictException("Account", "email");
        }

        return _accountRepository.update(account);
    }

    @Override
    public Account removeAccount(UUID accountId) {
        if (!_accountRepository.exists(accountId)) {
            throw new EntityNotFoundException("Account", accountId);
        }

        return _accountRepository.delete(accountId);
    }
}
