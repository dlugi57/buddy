package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import com.payMyBuddy.buddy.service.ContactsService;
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
public class ContactController {

    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    ContactsService contactsService;

    /**
     * Field injection of contact service
     *
     * @param contactsService initialization of contact service
     */
    @Autowired
    public ContactController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    /**
     * Add contact
     *
     * @param contact contact object
     * @return status and uri with new created contact
     */
    @PostMapping(value = "/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addContact(@Valid @RequestBody Contacts contact) {

        // if user already exist send status and error message
        if (!contactsService.addContact(contact)) {
            logger.error("POST contact -> " +
                    "addContact /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This user already exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This contact already exist");
        }
        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam(
                "user", contact.getUser().getId()).build().toUri();

        logger.info("POST contact -> addContact /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ " +
                "Result : '{}'.", location);

        return ResponseEntity.created(location).build();
    }

    /**
     * Delete contact
     *
     * @param contact contact object
     */
    @DeleteMapping(value = "/contact")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContact(@Valid @RequestBody Contacts contact) {
        // if there is no medical record send status and error message
        if (!contactsService.deleteContact(contact)) {
            logger.error("DELETE contact -> deleteContact /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This contact don't exist : '{}'.", contact.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user don't exist");
        }
        logger.info("DELETE contact -> deleteContact /**/ HttpStatus : " + HttpStatus.OK);
    }

    /**
     * Get contacts by user id
     *
     * @param user id of user
     * @return List of contacts
     */
    @GetMapping(value = "/contacts/{user}")
    public List<Contacts> getContactsByUserId(@PathVariable Integer user) {

        List<Contacts> contacts = contactsService.getContactsByUserId(user);

        if (contacts == null || contacts.isEmpty()) {

            logger.error("GET contacts -> getContactsByUserId /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no contacts in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no contacts in the " +
                    "data base");
        }
        logger.info("GET contacts -> getContactsByUserId /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", contacts.toString());

        return contacts;
    }

    /**
     * Get all contacts
     *
     * @return List of contacts
     */
    @GetMapping(value = "/contacts")
    public List<Contacts> getContacts() {
        List<Contacts> contacts = contactsService.getContacts();

        if (contacts == null || contacts.isEmpty()) {

            logger.error("GET contacts -> getContacts /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no contacts in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no contacts in the " +
                    "data base");
        }
        logger.info("GET contacts -> getContacts /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", contacts.toString());

        return contacts;
    }
}
