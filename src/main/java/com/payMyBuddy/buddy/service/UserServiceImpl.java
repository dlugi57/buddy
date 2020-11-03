package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User data manipulation
 */
@Service
public class UserServiceImpl implements UserService {

    static final Logger logger = LogManager
            .getLogger(UserServiceImpl.class);

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
     * @param user user object
     * @return true when success
     */
    @Override
    public boolean addUser(User user) {
        return userDao.save(user).getId() > 0;
    }

    /**
     * Update user
     *
     * @param user user object
     * @return true when success
     */
    @Override
    public boolean updateUser(User user) {
        if (userDao.existsByEmail(user.getEmail())) {

            return userDao.save(user).getId() > 0;

        }
        return false;
    }

    /**
     * Delete user
     *
     * @param user user object
     * @return true when success
     */
    @Override
    public boolean deleteUser(User user) {
        if (userDao.existsById(user.getId())) {
            try {
                userDao.delete(user);

                return true;

            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
        return false;
    }

    /**
     * Connect user
     *
     * @param email    email
     * @param password password
     * @return User object
     */
    @Override
    public User connectUser(String email, String password) {

        Optional<User> user = userDao.getByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }

        return null;
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
