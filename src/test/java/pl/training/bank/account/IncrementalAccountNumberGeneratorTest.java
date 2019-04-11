package pl.training.bank.account;

import org.junit.jupiter.api.Test;

import static java.lang.Long.parseLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncrementalAccountNumberGeneratorTest {

    private static final String ACCOUNT_NUMBER_FORMAT = "\\d{26}";

    private IncrementalAccountNumberGenerator accountNumberGenerator = new IncrementalAccountNumberGenerator();

    @Test
    void shouldGenerateValidAccountNumber() {
        assertTrue(accountNumberGenerator.next().matches(ACCOUNT_NUMBER_FORMAT));
    }

    @Test
    void shouldGenerateAccountNumberIncreasingPreviousOne() {
       String initialAccountNumber = accountNumberGenerator.next();
       assertEquals(1, parseLong(accountNumberGenerator.next()) - parseLong(initialAccountNumber));
    }

}
