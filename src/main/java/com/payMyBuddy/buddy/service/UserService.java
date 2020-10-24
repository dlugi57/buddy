package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);

    List<User> getUsers();

    boolean updateUser(User user);

    boolean deleteUser(User user);

    User connectUser(String email, String password);
}
