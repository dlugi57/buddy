package com.payMyBuddy.buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.TransferType;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.BankAccountService;
import com.payMyBuddy.buddy.service.BankTransferService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankTransferController.class)
class BankTransferControllerTest extends ControllerConfig{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankTransferService service;

    ObjectMapper mapper = new ObjectMapper();
    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static BankAccount bankAccount = new BankAccount("1234", "BANKNAME", user);
    public static BankTransfer bankTransfer = new BankTransfer(1000.0, bankAccount,
            TransferType.INCOMING, today, "description");

    @Test
    void addBankTransfer() throws Exception{
        when(service.addBankTransfer(any(BankTransfer.class))).thenReturn(true);

        this.mockMvc.perform(post("/bankTransfer")
                .content((mapper.writeValueAsString(bankTransfer)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void addBankTransferInvalid() throws Exception{
        when(service.addBankTransfer(any(BankTransfer.class))).thenReturn(false);

        this.mockMvc.perform(post("/bankTransfer")
                .content((mapper.writeValueAsString(bankTransfer)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    public static List<BankTransfer> bankTransfers = new ArrayList<>();

    static {
        bankTransfers.add(bankTransfer);
        bankTransfers.add(bankTransfer);
        bankTransfers.add(bankTransfer);
    }

    @Test
    void getBankTransfersByUserId() throws Exception {
        when(service.getBankTransferByUserId(anyInt())).thenReturn(bankTransfers);

        this.mockMvc.perform(get("/bankTransfers/3")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getBankTransfersByUserId_Null() throws Exception {
        when(service.getBankTransferByUserId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/bankTransfers/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankTransfersByUserId_Empty() throws Exception {
        when(service.getBankTransferByUserId(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/bankTransfers/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankTransfers()throws Exception {

        when(service.getBankTransfers()).thenReturn(bankTransfers);

        this.mockMvc.perform(get("/bankTransfers")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getBankTransfersNull() throws Exception {

        when(service.getBankTransfers()).thenReturn(null);

        this.mockMvc.perform(get("/bankTransfers")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankTransfersEmpty() throws Exception {

        when(service.getBankTransfers()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/bankTransfers")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}