package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User data manipulation
 */
@Service
public class UserServiceImpl implements UserService {

    // initialize objects
    UserDao userDao;

    /**
     * Field injection of user dao
     *
     * @param userDao user dao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    /**
     * Add users
     *
     * @return List of all users
     */
    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }


    /**
     * Get all users
     *
     * @return List of all users
     */
    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }



}
