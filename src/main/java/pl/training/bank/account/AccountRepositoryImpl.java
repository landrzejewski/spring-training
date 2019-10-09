package pl.training.bank.account;

import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AccountRepositoryImpl implements AccountRepositoryCustom {
    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getLastAccountNumber() {
        return entityManager.createNamedQuery(Account.SELECT_LAST_ACCOUNT_NUMBER, String.class)
                .getSingleResult();
    }

}
