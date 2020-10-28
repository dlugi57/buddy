package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.service.BankAccountService;
import com.payMyBuddy.buddy.service.BankTransferService;
import com.payMyBuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BankTransferController {

    static final Logger logger = LogManager
            .getLogger(BankTransferController.class);

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
        if (!bankTransferService.addBankTransfer(bankTransfer)) {
            logger.error("POST bankTransfer -> " +
                    "addBankTransfer /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " There is no bank account to make transfer");

            throw new ResponseStatusException(HttpStatus.CONFLICT, " There is no bank account to make transfer");
        }

        logger.info("POST bankTransfer -> addBankTransfer /**/ HttpStatus : " + HttpStatus.CREATED);
    }

    /**
     * Get bank transfer by user id
     *
     * @param user id of user
     * @return List of bank transfers
     */
    @GetMapping(value = "/bankTransfers/{user}")
    public List<BankTransfer> getBankTransfersByUserId(@PathVariable Integer user) {
        List<BankTransfer> bankTransfers = bankTransferService.getBankTransferByUserId(user);

        if (bankTransfers == null || bankTransfers.isEmpty()) {

            logger.error("GET bankTransfers -> getBankTransfersByUserId /**/ HttpStatus : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : User with this id " + user + " don't exist or this user " +
                    "don't have bank transfer");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with this id " + user + " don't exist or this user don't have bank account");
        }

        logger.info("GET bankTransfers -> getBankTransfersByUserId /**/ HttpStatus : " + HttpStatus.OK +
                " /**/ Result : '{}'.", bankTransfers.toString());

        return bankTransfers;
    }

    /**
     * Get all bank transfers
     *
     * @return List of bank transfers
     */
    @GetMapping(value = "/bankTransfers")
    public List<BankTransfer> getBankTransfers() {
        List<BankTransfer> bankTransfers = bankTransferService.getBankTransfers();

        if (bankTransfers == null || bankTransfers.isEmpty()) {

            logger.error("GET bankTransfers -> getBankTransfers /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no bank transfer in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no bank transfer in" +
                    " the data base");
        }
        logger.info("GET bankTransfers -> getBankTransfers /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", bankTransfers.toString());

        return bankTransfers;
    }

}
