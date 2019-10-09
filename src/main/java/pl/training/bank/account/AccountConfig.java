package pl.training.bank.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountNumberGenerator accountNumberGenerator(AccountRepository accountRepository) {
        return new JpaIncrementalAccountNumberGenerator(accountRepository);
    }

    @Bean
    public AccountService accountService(AccountNumberGenerator accountNumberGenerator, AccountRepository accountRepository) {
        return new AccountService(accountNumberGenerator, accountRepository);
    }

}