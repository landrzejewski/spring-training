package pl.training.bank.operation;

import org.junit.jupiter.api.Test;
import pl.training.bank.account.Account;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DepositTest {

    private static final long FUNDS = 1_000;

    private Account account = mock(Account.class);

    @Test
    void shouldDepositFundsOnAccount() {
        new Deposit().execute(account, FUNDS);
        verify(account).deposit(FUNDS);
    }

}
