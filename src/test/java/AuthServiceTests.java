import org.galax1y.entities.Account;

import org.galax1y.entities.Session;
import org.galax1y.errors.EntityNotFoundException;

import org.galax1y.errors.InvalidCredentialsException;
import org.galax1y.repositories.IAccountRepository;
import org.galax1y.repositories.ISessionRepository;
import org.galax1y.repositories.InMemoryAccountRepository;
import org.galax1y.repositories.InMemorySessionRepository;

import org.galax1y.services.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Auth Service unit tests")
public class AuthServiceTests {
    private IAccountRepository accountRepository;
    private ISessionRepository sessionRepository;
    private AuthService authService;

    @BeforeEach
    void Setup() {
        sessionRepository = new InMemorySessionRepository();
        accountRepository = new InMemoryAccountRepository();
        authService = new AuthService(sessionRepository, accountRepository);
    }

    @Test
    @DisplayName("Should be able to login with valid credentials")
    void TestFor_ValidLogin() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);

        Session response = authService.login("lucas@test.com", "p4ssw0rd");

        assertEquals(response.getAccountId(), mockAccount.getId());
        assertEquals(1, sessionRepository.getAll().size());
    }

    @Test
    @DisplayName("Should not be able to login with invalid credentials")
    void TestFor_InvalidLogin() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);

        assertThrows(InvalidCredentialsException.class, () -> authService.login("lucas@test.com", "invalid"));
        assertEquals(0, sessionRepository.getAll().size());
    }

    @Test
    @DisplayName("Should be able to create a valid new session")
    void TestFor_CreateSession() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);

        var response = authService.createSession(mockAccount.getId());

        assertEquals(1, sessionRepository.getAll().size());
        assertEquals(mockAccount.getId(), response.getAccountId());
    }

    @Test
    @DisplayName("Should be able to override older sessions")
    void TestFor_TwoSessionsForSameAccount() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);

        authService.createSession(mockAccount.getId());
        authService.createSession(mockAccount.getId());

        assertEquals(1, sessionRepository.getAll().size());
    }

    @Test
    @DisplayName("Should be able to validate a session")
    void TestFor_GetSessionById() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);
        Session mockSession = Session.create(mockAccount.getId());
        sessionRepository.create(mockSession);

        boolean response = authService.validateSession(mockSession);

        assertTrue( response);
    }

    @Test
    @DisplayName("Should be able to remove a session")
    void TestFor_RemoveSession() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");
        accountRepository.create(mockAccount);
        Session mockSession = Session.create(mockAccount.getId());
        sessionRepository.create(mockSession);

        authService.removeSession(mockSession);

        assertEquals(0, this.sessionRepository.getAll().size());
        assertThrows(EntityNotFoundException.class, () -> authService.validateSession(mockSession));
    }

    @Test
    @DisplayName("Should not be able to create a session for a non-existent account")
    void TestFor_CreateSessionWithNonExistentAccount() {
        Account mockAccount = Account.create("Lucas", "lucas@test.com", "p4ssw0rd");

        assertThrows(EntityNotFoundException.class, () -> authService.createSession(mockAccount.getId()));
    }
}
