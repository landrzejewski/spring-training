package pl.training.bank.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountRestController.class)
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {

    private static final long ACCOUNT_ID = 1;
    private static final String ACCOUNT_NUMBER = "00000000000000000000000001";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountMapper mapper;

    @BeforeEach
     void setUp() {
        Account account = new Account(ACCOUNT_NUMBER);
        account.setId(1L);
        AccountTo accountTo = new AccountTo();
        accountTo.setNumber(ACCOUNT_NUMBER);
        when(mapper.map(account)).thenReturn(accountTo);
        //when(accountService.getAccounts(0,1)).thenReturn(accounts);
        given(accountService.getById(ACCOUNT_ID)).willReturn(account);
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(ACCOUNT_NUMBER)))
                .andExpect(jsonPath("$.balance", is(0)));
    }

}
