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


        // if medical record already exist send status and error message
        //if (!userService.addUser(user)) {
            //logger.error("POST medicalrecord -> " +
              //      "addMedicalRecord /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/
            //      Message :  This medical record already exist");

          //  throw new ResponseStatusException(HttpStatus.CONFLICT, "This medical record already
            //  exist");
        //}
        userService.addUser(user);
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName")
                .queryParam("lastName").build().toUri();

        logger.info("POST medicalrecord -> addMedicalRecord /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ Result : '{}'.", location);

        return ResponseEntity.created(location).build();
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
