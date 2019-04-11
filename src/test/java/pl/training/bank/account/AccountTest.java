package pl.training.bank.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private static final long FUNDS = 1_000;
    private Account account = new Account();
    private long initialBalance = account.getBalance();

    @Test
    void shouldIncreaseBalanceAfterDeposit() {
        account.deposit(FUNDS);
        assertEquals(initialBalance + FUNDS, account.getBalance());
    }

    @Test
    void shouldDecreaseBalanceAfterWithdraw() {
        account.withdraw(FUNDS);
        assertEquals(initialBalance - FUNDS, account.getBalance());
    }

}
