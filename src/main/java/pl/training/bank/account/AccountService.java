package pl.training.bank.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;
import pl.training.bank.common.ResultPage;

@Transactional
@RequiredArgsConstructor
public class AccountService {

    @NonNull
    private AccountNumberGenerator accountNumberGenerator;
    @NonNull
    private AccountRepository accountRepository;
    @NonNull
    private TokenStore tokenStore;

    public Account create() {
        String accountNumber = accountNumberGenerator.next();
        Account account = new Account(accountNumber);
        return accountRepository.save(account);
    }

    public Account getByNumber(String accountNumber) {
        return accountRepository.getByNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
    }

    public Account getById(Long id) {
        return accountRepository.getById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    public ResultPage<Account> get(int pageNumber, int pageSize) {
        Page<Account> accountsPage = accountRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResultPage<>(accountsPage.getContent(), pageNumber, accountsPage.getTotalPages());
    }

    public void update(Account account) {
        getByNumber(account.getNumber());
        accountRepository.save(account);
    }

    public void logout(String tokenValue) {
        OAuth2AccessToken token = tokenStore.readAccessToken(tokenValue);
        tokenStore.removeRefreshToken(token.getRefreshToken());
        tokenStore.removeAccessToken(token);
    }

}
