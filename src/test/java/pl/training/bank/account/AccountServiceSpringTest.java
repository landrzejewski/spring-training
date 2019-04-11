package pl.training.bank.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AccountServiceSpringTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public AccountService accountService(AccountNumberGenerator accountNumberGenerator, AccountRepository accountRepository) {
            return new AccountService(accountNumberGenerator, accountRepository);
        }

    }

    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountNumberGenerator accountNumberGenerator;
    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        when(accountRepository.getById(anyLong())).thenReturn(Optional.empty());
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getById(1L));
    }

}
