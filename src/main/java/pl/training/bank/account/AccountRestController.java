package pl.training.bank.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.bank.common.ResultPage;
import pl.training.bank.common.UriBuilder;
import pl.training.bank.common.to.PageTo;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "${baseUrl}/accounts")
@RestController
public class AccountRestController {

    @NonNull
    private AccountMapper accountMapper;
    @NonNull
    private AccountService accountService;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAccount() {
        Account account = accountService.create();
        URI uri = uriBuilder.requestUriWithId(account.getId());
        return ResponseEntity.created(uri).body(accountMapper.map(account));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public AccountTo getAccountById(@PathVariable("id") Long id) {
        Account account = accountService.getById(id);
        return accountMapper.map(account);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAccounts(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ResultPage<Account> accountsPage = accountService.get(pageNumber, pageSize);
        List<AccountTo> accountTos = accountMapper.map(accountsPage.getData());
        PageTo pageDto = new PageTo<>(accountTos, accountsPage.getPageNumber(), accountsPage.getTotalPages());
        return ResponseEntity.ok(pageDto);
    }

}
