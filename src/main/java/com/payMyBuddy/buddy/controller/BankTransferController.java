package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.service.BankAccountService;
import com.payMyBuddy.buddy.service.BankTransferService;
import com.payMyBuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class BankTransferController {

    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    UserService userService;
    BankAccountService bankAccountService;
    BankTransferService bankTransferService;


    /**
     * Field injection of user service
     *
     * @param userService initialization of user service
     */
    @Autowired
    public BankTransferController(UserService userService, BankAccountService bankAccountService,
                                  BankTransferService bankTransferService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
        this.bankTransferService = bankTransferService;
    }

    /**
     * Make transfer
     *
     * @param bankTransfer bank Transfer object
     */
    @PostMapping(value = "/bankTransfer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBankTransfer(@Valid @RequestBody BankTransfer bankTransfer) {

        // if user already exist send status and error message
        if (bankTransferService.addBankTransfer(bankTransfer) <= 0) {
            logger.error("POST bankTransfer -> " +
                    "addBankTransfer /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This Bank Account already exist or there is no users with this id");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This bank account already " +
                    "exist or there is no users with this id");
        }

        logger.info("POST bankTransfer -> addBankTransfer /**/ HttpStatus : " + HttpStatus.CREATED);
    }

}
