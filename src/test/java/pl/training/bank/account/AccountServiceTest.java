package pl.training.bank.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private static final String ACCOUNT_NUMBER = "00000000000000000000000001";

    private AccountNumberGenerator accountNumberGenerator = mock(AccountNumberGenerator.class);
    private AccountRepository accountRepository = mock(AccountRepository.class);
    private AccountService accountService = new AccountService(accountNumberGenerator, accountRepository);

    @BeforeEach
    void setUp() {
        when(accountNumberGenerator.next()).thenReturn(ACCOUNT_NUMBER);
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
    }

    @Test
    void shouldSaveCreatedAccount() {
        Account account = accountService.create();
        verify(accountRepository).save(account);
    }

    @Test
    void shouldAssignNumberToCreatedAccount() {
        Account account = accountService.create();
        assertEquals(ACCOUNT_NUMBER, account.getNumber());
    }

}
