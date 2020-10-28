package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.service.BankAccountService;
import com.payMyBuddy.buddy.service.BankTransferService;
import com.payMyBuddy.buddy.service.TransferService;
import com.payMyBuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TransferController {


    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    UserService userService;
    TransferService transferService;


    /**
     * Field injection of user service
     *
     * @param userService initialization of user service
     */
    @Autowired
    public TransferController(UserService userService, TransferService transferService,
                                  BankTransferService bankTransferService) {
        this.userService = userService;
        this.transferService = transferService;
    }
}
