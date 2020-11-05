package com.payMyBuddy.buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.model.Transfer;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.TransferService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransferController.class)
class TransferControllerTest extends ControllerConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService service;


    ObjectMapper mapper = new ObjectMapper();
    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static User contact = new User("John", "Boyd", "jaboyd1@email.com", "password", today);
    public static Transfer transfer = new Transfer(1000.0, user, contact, today, "description");

    @Test
    void addTransfer() throws Exception {

        when(service.addTransfer(any(Transfer.class))).thenReturn(true);

        this.mockMvc.perform(post("/transfer")
                .content((mapper.writeValueAsString(transfer)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void addTransferInvalid() throws Exception {

        when(service.addTransfer(any(Transfer.class))).thenReturn(false);

        this.mockMvc.perform(post("/transfer")
                .content((mapper.writeValueAsString(transfer)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    public static List<Transfer> transfers = new ArrayList<>();

    static {
        transfers.add(transfer);
        transfers.add(transfer);
        transfers.add(transfer);
    }

    @Test
    void getTransfersByUserId() throws Exception {
        when(service.getTransfersByUserId(anyInt())).thenReturn(transfers);

        this.mockMvc.perform(get("/transfers/3")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getTransfersByUserId_Null() throws Exception {
        when(service.getTransfersByUserId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/transfers/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransfersByUserId_Empty() throws Exception {
        when(service.getTransfersByUserId(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/transfers/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransfers() throws Exception {
        when(service.getTransfers()).thenReturn(transfers);

        this.mockMvc.perform(get("/transfers")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getTransfers_Null() throws Exception {
        when(service.getTransfers()).thenReturn(null);

        this.mockMvc.perform(get("/transfers")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransfers_Empty() throws Exception {
        when(service.getTransfers()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/transfers")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}