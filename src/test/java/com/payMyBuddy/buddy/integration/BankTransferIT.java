package com.payMyBuddy.buddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.dao.BankTransferDao;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.TransferType;
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
public class BankTransferIT {
    @Autowired
    BankTransferDao bankTransferDao;

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
    public static BankTransfer bankTransfer = new BankTransfer(1000.0, bankAccount,
            TransferType.INCOMING, today, "description");

    @Order(1)
    @Test
    public void addBankTransfer() throws Exception {

        bankAccount.setId(1);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/bankTransfer")
                .contentType("application/json")
                .content(mapper.writeValueAsString(bankTransfer)))
                .andExpect(status().isCreated());

        bankTransfer.setId(8);
        Optional<BankTransfer> bankTransferAdd = bankTransferDao.getById(bankTransfer.getId());

        assert bankTransferAdd.orElse(null) != null;
        bankTransfer.setCreationDate(bankTransferAdd.orElse(null).getCreationDate());
        assertNotNull(bankTransferAdd.orElse(null));
    }
}
