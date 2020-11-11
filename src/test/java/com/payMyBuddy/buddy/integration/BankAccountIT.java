package com.payMyBuddy.buddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Rollback()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountIT {
    @Autowired
    BankAccountDao bankAccountDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    static Date today = Date.from(Instant.parse("2020-10-31T22:40:25.737000Z"));
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static BankAccount bankAccount = new BankAccount("1234", "BANKNAME", user);

    @Order(1)
    @Test
    public void addBankAccount() throws Exception {

        user.setId(1);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/bankAccount")
                .contentType("application/json")
                .content(mapper.writeValueAsString(bankAccount)))
                .andExpect(status().isCreated());

        bankAccount.setId(6);
        Optional<BankAccount> bankTransferAdd = bankAccountDao.getById(bankAccount.getId());

        assertNotNull(bankTransferAdd.orElse(null));
    }
}
