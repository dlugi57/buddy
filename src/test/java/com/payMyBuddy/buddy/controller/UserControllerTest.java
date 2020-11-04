package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.UserService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void addUser() throws Exception {

        when(service.addUser(any(User.class))).thenReturn(true);

        this.mockMvc.perform(post("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/user/?firstName" +
                        "=UserFirstName&lastName=UserLastName"))
                .andExpect(status().isCreated());
    }

    @Test
    void addUserInvalid() throws Exception {

        when(service.addUser(any(User.class))).thenReturn(false);

        this.mockMvc.perform(post("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser() throws Exception {
        when(service.updateUser(any(User.class))).thenReturn(true);

        this.mockMvc.perform(put("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/user/?firstName" +
                        "=UserFirstName&lastName=UserLastName"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUserInvalid() throws Exception {
        when(service.updateUser(any(User.class))).thenReturn(false);

        this.mockMvc.perform(put("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser()throws Exception {
        when(service.deleteUser(any(User.class))).thenReturn(true);

        this.mockMvc.perform(delete("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserInvalid()throws Exception {
        when(service.deleteUser(any(User.class))).thenReturn(false);

        this.mockMvc.perform(delete("/user")
                .content("{\"firstName\":\"UserFirstName\",\"lastName\":\"UserLastName\"," +
                        "\"email\":\"user@email.com\", \"password\":\"password\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    static Date today = new Date();
    @Test
    void connectUser()throws Exception {

        when(service.connectUser(anyString(),
                anyString())).thenReturn(new User("John", "Boyd", "jaboyd@email.com", "password",today
                ));

        this.mockMvc.perform(get("/user")
                .param("email", "jaboyd@email.com")
                .param("password", "password")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test
    void connectUserInvalid() throws Exception {

        when(service.connectUser(anyString(),
                anyString())).thenReturn(null);

        this.mockMvc.perform(get("/user")
                .param("email", "jaboyd@email.com")
                .param("password", "password")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    public static List<User> usersList = new ArrayList<>();
    static {
        usersList.add(new User("John", "Boyd", "jaboyd@email.com", "password", today));
        usersList.add(new User("Jacob", "Boyd",  "drk@email.com", "password", today));
        usersList.add(new User("Tenley", "Boyd", "tenz@email.com", "password", today));
    }

    @Test
    void getUsers() throws Exception{
        when(service.getUsers()).thenReturn(usersList);

        this.mockMvc.perform(get("/users")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void getUsersNull() throws Exception {

        when(service.getUsers()).thenReturn(null);

        this.mockMvc.perform(get("/users")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsersEmpty() throws Exception {

        when(service.getUsers()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/users")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}