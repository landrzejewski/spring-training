package pl.training.bank.account.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import pl.training.bank.account.entity.Account;
import pl.training.bank.account.repository.AccountRepository;
import pl.training.bank.common.aop.ResultPage;
import pl.training.bank.generator.service.AccountNumberGenerator;

@Transactional
@Log
@AllArgsConstructor
@NoArgsConstructor
public class AccountService {

    private AccountNumberGenerator accountNumberGenerator;
    private AccountRepository accountRepository;

    public Account createAccount() {
        String accountNumber = accountNumberGenerator.getNext();
        Account account = new Account(accountNumber);
        return accountRepository.save(account);
    }

    public ResultPage<Account> getAccounts(int pageNumber, int pageSize) {
        Page<Account> result = accountRepository.findAll(new PageRequest(pageNumber, pageSize));
        return new ResultPage<>(result.getContent(), pageNumber, result.getTotalPages());
    }

    public Account getAccountById(Long id) {
        return accountRepository.getById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }

}
