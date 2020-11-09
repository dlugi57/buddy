package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Resource
    UserService userService;

    @MockBean
    UserDao userDao;

    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);

    @Test
    void addUser() {
        // GIVEN
        user.setId(1);
        given(userDao.save(any(User.class))).willReturn(user);
        // WHEN
        boolean userTest = userService.addUser(new User("John", "Boyd", "jaboyd@email.com",
                "password", today));
        // THEN
        verify(userDao, times(1)).save(any(User.class));

        assertThat(userTest).isEqualTo(true);
    }

    @Test
    void addUser_DontExist() {
        // GIVEN
        //user.setId(1);
        given(userDao.save(any(User.class))).willReturn(null);
        // WHEN
        boolean userTest = userService.addUser(new User("John", "Boyd", "jaboyd@email.com",
                "password", today));
        // THEN
        verify(userDao, times(1)).save(any(User.class));

        assertThat(userTest).isEqualTo(false);
    }

    @Test
    void updateUser() {
        // GIVEN
        given(userDao.existsByEmail(anyString())).willReturn(true);
        user.setId(1);
        given(userDao.save(any(User.class))).willReturn(user);
        // WHEN
        boolean userTest = userService.updateUser(new User("John", "Boyd", "jaboyd@email.com",
                "password", today));
        // THEN
        verify(userDao, times(1)).existsByEmail(anyString());
        verify(userDao, times(1)).save(any(User.class));

        assertThat(userTest).isEqualTo(true);
    }

    @Test
    void updateUser_DontExist() {
        // GIVEN
        given(userDao.existsByEmail(anyString())).willReturn(false);
        // WHEN
        boolean userTest = userService.updateUser(new User("John", "Boyd", "jaboyd@email.com",
                "password", today));
        // THEN
        verify(userDao, times(1)).existsByEmail(anyString());

        assertThat(userTest).isEqualTo(false);
    }

    @Test
    void deleteUser() {
        // GIVEN
        given(userDao.existsById(anyInt())).willReturn(true);
        user.setId(1);

        // WHEN
        boolean userTest = userService.deleteUser(user);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        verify(userDao, times(1)).delete(any(User.class));

        assertThat(userTest).isEqualTo(true);
    }

    @Test
    void deleteUser_Invalid() {
        // GIVEN
        given(userDao.existsById(anyInt())).willReturn(false);
        user.setId(1);

        // WHEN
        boolean userTest = userService.deleteUser(user);

        // THEN
        verify(userDao, times(1)).existsById(anyInt());

        assertThat(userTest).isEqualTo(false);
    }

    @Test
    void connectUser() {

        // GIVEN
        user.setId(1);
        given(userDao.getByEmail(anyString())).willReturn(Optional.of(user));

        // WHEN
        User userTest = userService.connectUser("jaboyd@email.com", "password");

        // THEN
        verify(userDao, times(1)).getByEmail(anyString());

        assertThat(userTest).isEqualTo(user);
    }

    public static List<User> users = new ArrayList<>();

    static {
        users.add(user);
        users.add(user);
        users.add(user);
    }

    @Test
    void getUsers() {
        // GIVEN
        given(userDao.findAll()).willReturn(users);
        // WHEN
        List<User> usersTest = userService.getUsers();
        // THEN
        verify(userDao, times(1)).findAll();
        assertThat(usersTest.size()).isEqualTo(3);
    }

    @Test
    void getUsers_Invalid() {
        // GIVEN
        given(userDao.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<User> usersTest = userService.getUsers();
        // THEN
        verify(userDao, times(1)).findAll();
        assertThat(usersTest).isEqualTo(Collections.emptyList());
    }
}