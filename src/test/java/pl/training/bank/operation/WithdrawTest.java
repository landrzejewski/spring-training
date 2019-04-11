package pl.training.bank.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.training.bank.account.Account;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WithdrawTest {

    private static final long FUNDS = 1_000;

    private Account account = mock(Account.class);

    @BeforeEach
    void setUp() {
        when(account.getBalance()).thenReturn(FUNDS);
    }

    @Test
    void shouldWithdrawFundsFromAccount() {
        new Withdraw().execute(account, FUNDS);
        verify(account).withdraw(FUNDS);
    }

    @Test
    void shouldThrowExceptionWhenThereIsInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> new Withdraw().execute(account, FUNDS + 1));
    }

}
