package com.payMyBuddy.buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.BankTransferDao;
import com.payMyBuddy.buddy.dao.TransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;

    // TODO: 03/11/2020 why i need to add this
    @MockBean
    UserDao userDao;

    @MockBean
    BankTransferDao bankTransferDao;

    @MockBean
    BankAccountDao bankAccountDao;

    @MockBean
    TransferDao transferDao;

    @MockBean
    private UserService service;

    @Test
    void addUser() throws Exception  {
/*        ObjectMapper mapper = new ObjectMapper();
        Date today = new Date();
        User user = new User();
        user.setEmail("user@email.com");
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("password");
        user.setCreationDate(today);*/
        when(service.addUser(any(User.class))).thenReturn(true);

        this.mockMvc.perform(post("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                       "\"email\":\"user@email.com\", \"password\":\"password\"}")
               // .content((mapper.writeValueAsString(user)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/user/?firstName" +
                        "=UserFirstName&lastName=UserLastName"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void connectUser() {
    }

    @Test
    void getUsers() {
    }
}