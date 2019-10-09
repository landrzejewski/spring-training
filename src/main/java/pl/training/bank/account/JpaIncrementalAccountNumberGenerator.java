package pl.training.bank.account;

public class JpaIncrementalAccountNumberGenerator extends IncrementalAccountNumberGenerator {

    public JpaIncrementalAccountNumberGenerator(AccountRepository accountRepository) {
        String lastAccountNumber = accountRepository.getLastAccountNumber();
        setCurrentAccountNumber(lastAccountNumber);
    }

}
