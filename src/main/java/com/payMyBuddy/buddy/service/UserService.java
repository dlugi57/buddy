package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> getUsers();
}
