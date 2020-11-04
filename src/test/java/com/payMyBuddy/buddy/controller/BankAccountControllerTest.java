package com.payMyBuddy.buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest extends ControllerConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService service;

    ObjectMapper mapper = new ObjectMapper();
    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static BankAccount bankAccount = new BankAccount("1234", "BANKNAME", user);

    @Test
    void addBankAccount() throws Exception {
        when(service.addBankAccount(any(BankAccount.class))).thenReturn(true);

        this.mockMvc.perform(post("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void addBankAccountInvalid() throws Exception {
        when(service.addBankAccount(any(BankAccount.class))).thenReturn(false);

        this.mockMvc.perform(post("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateBankAccount() throws Exception {
        when(service.updateBankAccount(any(BankAccount.class))).thenReturn(true);

        this.mockMvc.perform(put("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateBankAccountInvalid() throws Exception {
        when(service.updateBankAccount(any(BankAccount.class))).thenReturn(false);

        this.mockMvc.perform(put("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void deleteBankAccount() throws Exception {
        when(service.deleteBankAccount(any(BankAccount.class))).thenReturn(true);

        this.mockMvc.perform(delete("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteBankAccountInvalid() throws Exception {
        when(service.deleteBankAccount(any(BankAccount.class))).thenReturn(false);

        this.mockMvc.perform(delete("/bankAccount")
                .content((mapper.writeValueAsString(bankAccount)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static List<BankAccount> bankAccounts = new ArrayList<>();

    static {
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
    }

    @Test
    void getBankAccountsByUserId() throws Exception {
        when(service.getBankAccountsByUserId(anyInt())).thenReturn(bankAccounts);

        this.mockMvc.perform(get("/bankAccounts/3")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void getBankAccountsByUserId_Null() throws Exception {
        when(service.getBankAccountsByUserId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/bankAccounts/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankAccountsByUserId_Empty() throws Exception {
        when(service.getBankAccountsByUserId(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/bankAccounts/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankAccounts() throws Exception {
        when(service.getBankAccounts()).thenReturn(bankAccounts);

        this.mockMvc.perform(get("/bankAccounts")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getBankAccountsNull() throws Exception {

        when(service.getBankAccounts()).thenReturn(null);

        this.mockMvc.perform(get("/bankAccounts")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankAccountsEmpty() throws Exception {

        when(service.getBankAccounts()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/bankAccounts")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}