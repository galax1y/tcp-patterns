package org.galax1y.services;

import org.galax1y.entities.Account;
import org.galax1y.entities.Session;
import org.galax1y.errors.EntityNotFoundException;
import org.galax1y.errors.InvalidCredentialsException;
import org.galax1y.repositories.IAccountRepository;
import org.galax1y.repositories.ISessionRepository;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

interface IAuthService {
    Session login(String email, String password);

    Session createSession(UUID accountId);
    void removeSession(Session session);
    boolean validateSession(Session session);

}

public class AuthService implements IAuthService {
    private final ISessionRepository _sessionRepository;
    private final IAccountRepository _accountRepository;

    public AuthService(ISessionRepository sessionRepository, IAccountRepository accountRepository) {
        this._sessionRepository = sessionRepository;
        this._accountRepository = accountRepository;
    }

    @Override
    public Session login(String email, String password) {
        Account account = _accountRepository.getByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!account.passwordsMatch(password)) {
            throw new InvalidCredentialsException();
        }

        Session newSession = Session.create(account.getId());

        return _sessionRepository.create(newSession);
    }

    @Override
    public Session createSession(UUID accountId) {
        if (!_accountRepository.exists(accountId)) {
            throw new EntityNotFoundException("Account", accountId);
        }

        return _sessionRepository.create(Session.create(accountId));
    }

    @Override
    public void removeSession(Session session) {
        _sessionRepository.getByAccountId(session.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Session", session.getAccountId()));

        _sessionRepository.remove(session);
    }

    @Override
    public boolean validateSession(Session session) {
        UUID accountId = session.getAccountId();

        _sessionRepository.getByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Session", accountId));

        Date now = Date.from(Instant.now());

        if (session.getExpiresAt().before(now)) {
            return false;
        }

        return true;
    }
}
