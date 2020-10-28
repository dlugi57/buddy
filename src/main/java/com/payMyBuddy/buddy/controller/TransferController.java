package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.Transfer;
import com.payMyBuddy.buddy.service.BankAccountService;
import com.payMyBuddy.buddy.service.BankTransferService;
import com.payMyBuddy.buddy.service.TransferService;
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

public class TransferController {


    static final Logger logger = LogManager
            .getLogger(TransferController.class);

    // Service initialization
    UserService userService;
    TransferService transferService;


    /**
     * Field injection of user service
     *
     * @param userService initialization of user service
     */
    @Autowired
    public TransferController(UserService userService, TransferService transferService) {
        this.userService = userService;
        this.transferService = transferService;
    }

    /**
     * Make transfer
     *
     * @param transfer transfer object
     */
    @PostMapping(value = "/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransfer(@Valid @RequestBody Transfer transfer) {

        // if user already exist send status and error message
        if (!transferService.addTransfer(transfer)) {
            logger.error("POST transfer -> " +
                    "addTransfer /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " There is no users to make this transfer");

            throw new ResponseStatusException(HttpStatus.CONFLICT, " There is no users to make this transfer");
        }

        logger.info("POST transfer -> addTransfer /**/ HttpStatus : " + HttpStatus.CREATED);
    }
}
