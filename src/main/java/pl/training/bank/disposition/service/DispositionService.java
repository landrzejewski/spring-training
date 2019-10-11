package pl.training.bank.disposition.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.training.bank.account.entity.Account;
import pl.training.bank.account.services.AccountNotFoundException;
import pl.training.bank.account.repository.AccountRepository;
import pl.training.bank.common.aop.ExecutionTime;
import pl.training.bank.disposition.entity.Disposition;
import pl.training.bank.operation.entity.Operation;
import pl.training.bank.operation.service.UnknownOperationException;

import java.util.Map;

@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class DispositionService {

    private AccountRepository accountRepository;
    private Map<String, Operation> operations;

    @ExecutionTime
    public void process(Disposition disposition) {
        Account account = getAccount(disposition.getAccountNumber());
        Operation operation = getOperation(disposition.getOperationName());
        operation.execute(account, disposition.getFunds());
        accountRepository.save(account);
    }

    private Account getAccount(String accountNumber) {
        return accountRepository.getByNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
    }

    private Operation getOperation(String operationName) {
        if (operationName == null || !operations.containsKey(operationName)) {
            throw new UnknownOperationException();
        }
        return operations.get(operationName);
    }

}
