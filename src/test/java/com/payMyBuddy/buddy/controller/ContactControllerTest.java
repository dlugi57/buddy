package com.payMyBuddy.buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.ContactsService;
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
@WebMvcTest(ContactController.class)
class ContactControllerTest extends ControllerConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactsService service;

    ObjectMapper mapper = new ObjectMapper();
    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static User contact = new User("John", "Boyd", "jaboyd1@email.com", "password", today);
    public static Contacts contacts = new Contacts(user, contact);

    @Test
    void addContact() throws Exception {
        when(service.addContact(any(Contacts.class))).thenReturn(true);

        this.mockMvc.perform(post("/contact")
                .content((mapper.writeValueAsString(contacts)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());

    }
    @Test
    void addContactInvalid() throws Exception {
        when(service.addContact(any(Contacts.class))).thenReturn(false);

        this.mockMvc.perform(post("/contact")
                .content((mapper.writeValueAsString(contacts)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());

    }
    @Test
    void deleteContact() throws Exception{
        when(service.deleteContact(any(Contacts.class))).thenReturn(true);

        this.mockMvc.perform(delete("/contact")
                .content((mapper.writeValueAsString(contacts)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deleteContactInvalid() throws Exception{
        when(service.deleteContact(any(Contacts.class))).thenReturn(false);

        this.mockMvc.perform(delete("/contact")
                .content((mapper.writeValueAsString(contacts)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static List<Contacts> contactsList = new ArrayList<>();

    static {
        contactsList.add(contacts);
        contactsList.add(contacts);
        contactsList.add(contacts);
    }

    @Test
    void getContactsByUserId() throws Exception {
        when(service.getContactsByUserId(anyInt())).thenReturn(contactsList);

        this.mockMvc.perform(get("/contacts/3")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    @Test
    void getContactsByUserId_Null() throws Exception {
        when(service.getContactsByUserId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/contacts/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getContactsByUserId_Empty() throws Exception {
        when(service.getContactsByUserId(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/contacts/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void getContacts() throws Exception {
        when(service.getContacts()).thenReturn(contactsList);

        this.mockMvc.perform(get("/contacts")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    @Test
    void getContacts_Null() throws Exception {
        when(service.getContacts()).thenReturn(null);

        this.mockMvc.perform(get("/contacts")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getContacts_Empty() throws Exception {
        when(service.getContacts()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/contacts")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}