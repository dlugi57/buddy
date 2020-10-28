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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * Get transfers by user id
     *
     * @param user id of user
     * @return List of transfers
     */
    @GetMapping(value = "/transfers/{user}")
    public List<Transfer> getTransfersByUserId(@PathVariable Integer user) {
        List<Transfer> transfers = transferService.getTransfersByUserId(user);

        if (transfers == null || transfers.isEmpty()) {

            logger.error("GET transfers -> getTransfersByUserId /**/ HttpStatus : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : User with this id " + user + " don't exist or this user " +
                    "don't have transfers");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with this id " + user + " don't exist or this user don't have transfers");
        }

        logger.info("GET transfers -> getTransfersByUserId /**/ HttpStatus : " + HttpStatus.OK +
                " /**/ Result : '{}'.", transfers.toString());

        return transfers;
    }

    /**
     * Get all transfers
     *
     * @return List of transfers
     */
    @GetMapping(value = "/transfers")
    public List<Transfer> getTransfers() {
        List<Transfer> transfers = transferService.getTransfers();

        if (transfers == null || transfers.isEmpty()) {

            logger.error("GET transfers -> getTransfers /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no transfer in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no transfer in" +
                    " the data base");
        }
        logger.info("GET transfers -> getTransfers /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", transfers.toString());

        return transfers;
    }

}
