import org.galax1y.entities.Account;

import org.galax1y.repositories.IAccountRepository;
import org.galax1y.repositories.InMemoryAccountRepository;

import org.galax1y.services.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account service unit tests")
public class AccountServiceTests {
    private IAccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void Setup() {
        accountRepository = new InMemoryAccountRepository();
        accountService = new AccountService(accountRepository);
    }

    @Test
    @DisplayName("Should be able to create an account")
    void TestFor_CreateAccount() {
        accountService.createAccount("Lucas", "lucas@test.com", "p4ssw0rd");

        assertEquals(1, accountRepository.getAll().size());
    }

    @Test
    @DisplayName("Should be able to update an account")
    void TestFor_UpdateAccount() {
        Account mockAccount = accountService.createAccount("Lucas", "lucas@test.com", "p4ssw0rd");
        String updatedEmail = "lucas_wermann@test.com";

        mockAccount.setEmail(updatedEmail);

        Account response = accountService.updateAccount(mockAccount);

        assertEquals(1, accountRepository.getAll().size());
        assertEquals(updatedEmail, response.getEmail());
    }

    @Test
    @DisplayName("Should be able to remove an account")
    void TestFor_RemoveAccount() {
        Account mockAccount = accountService.createAccount("Lucas", "lucas@test.com", "p4ssw0rd");

        assertEquals(1, accountRepository.getAll().size());

        accountService.removeAccount(mockAccount.getId());

        assertEquals(0, accountRepository.getAll().size());
    }
}
