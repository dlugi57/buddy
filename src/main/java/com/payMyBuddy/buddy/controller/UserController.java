package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    UserService userService;

    /**
     * Field injection of user service
     *
     * @param userService initialization of user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add user
     *
     * @param user user object
     * @return status and uri with new created user
     */
    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user) {

        // if user already exist send status and error message
        if (!userService.addUser(user)) {
            logger.error("POST user -> " +
                    "addUser /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This user already exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already exist");
        }
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", user.getFirstName())
                .queryParam("lastName", user.getLastName()).build().toUri();

        logger.info("POST user -> addUser /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.", location);

        return ResponseEntity.created(location).build();
    }

    /**
     * Add user
     *
     * @param user user object
     * @return status and uri with new created user
     */
    @PutMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {

        // if user already exist send status and error message
        if (!userService.updateUser(user)) {
            logger.error("POST user -> " +
                    "updateUser /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This user don't exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user don't exist");
        }
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", user.getFirstName())
                .queryParam("lastName", user.getLastName()).build().toUri();

        logger.info("POST user -> updateUser /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.", location);

        return ResponseEntity.created(location).build();
    }

    /**
     * Delete medical record
     *
     * @param user user record object
     */
    @DeleteMapping(value = "/user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Valid @RequestBody User user) {
        // if there is no medical record send status and error message
        if (!userService.deleteUser(user)) {
            logger.error("DELETE user -> deleteUser /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This user don't exist : '{}'.", user.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user don't exist");
        }
        logger.info("DELETE user -> deleteUser /**/ HttpStatus : " + HttpStatus.OK);
    }

    /**
     * Connect user
     *
     * @param email    email
     * @param password password
     * @return User object
     */
    @GetMapping(value = "/user")
    @ResponseStatus(HttpStatus.OK)
    public User connectUser(@RequestParam() String email, String password) {
        // get person
        User user = userService.connectUser(email, password);
        // if person don't exist send error message
        if (user == null) {
            logger.error("GET user -> getPersonByFirstNameAndLastName /**/ HttpStatus : " + HttpStatus.NOT_FOUND + " /**/ Message :  User email " + email + " or password " + password + " are wrong");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Wrong email: " + email + " or password " + password);
        }

        logger.info("GET person -> getPersonByFirstNameAndLastName /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", user.toString());
        return user;
    }

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
