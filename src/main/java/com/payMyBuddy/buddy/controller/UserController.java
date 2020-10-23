package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    UserService userService;

    /**
     * Get all users
     *
     * @return List of users
     */
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        List<User> users = userService.getUsers();

        if (users == null || users.isEmpty()) {
            logger.error("GET users -> getUsers /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no users in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no users in the " +
                    "data base");
        }
        logger.info("GET users -> getUsers /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", users.toString());

        return users;
    }
}
