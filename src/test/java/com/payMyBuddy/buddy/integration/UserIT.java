package com.payMyBuddy.buddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.dao.UserDao;
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

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Rollback()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIT {
    // TODO: 10/11/2020 how to add global variables to the it tests
    @Autowired
    UserDao userDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    static Date today = new Date();

    @Order(5)
    @Test
    public void addUser() throws Exception {
// TODO: 10/11/2020 wtf 
        User userTest = new User("John", "Boyd", "jaboyd@email.com", "password",today
        );

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(mapper.writeValueAsString(userTest)))
                .andExpect(status().isCreated());



        Optional<User> userAdd = userDao.getByEmail(userTest.getEmail());
        userTest.setId(6);
        assertNotNull(userAdd.get());
        assertThat(mapper.writeValueAsString(userTest)).isEqualTo(mapper.writeValueAsString(userAdd.get()));
    }

}
