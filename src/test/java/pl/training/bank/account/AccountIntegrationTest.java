package pl.training.bank.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.training.bank.Application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AccountIntegrationTest {

    private static final String ACCOUNT_NUMBER = "00000000000000000000000001";

    @Autowired
    private MockMvc mockMvc;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Test
    public void shouldReturnAccountById() throws Exception {
        entityManager.persist(new Account(ACCOUNT_NUMBER));
        mockMvc.perform(get("/api/v1/accounts/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(ACCOUNT_NUMBER)))
                .andExpect(jsonPath("$.balance", is(0)));
    }

}
