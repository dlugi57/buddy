package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BankAccountController {

    static final Logger logger = LogManager
            .getLogger(UserController.class);

    // Service initialization
    BankAccountService bankAccountService;

    /**
     * Field injection of bank account service
     *
     * @param bankAccountService initialization of bank account service
     */
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Add bank account
     *
     * @param bankAccount bankAccount object
     */
    @PostMapping(value = "/bankAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBankAccount(@Valid @RequestBody BankAccount bankAccount) {

        // if user already exist send status and error message
        if (!bankAccountService.addBankAccount(bankAccount)) {
            logger.error("POST bankAccount -> " +
                    "addBankAccount /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This Bank Account already exist or there is no users with this id");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This bank account already " +
                    "exist or there is no users with this id");
        }

        logger.info("POST bankAccount -> addBankAccount /**/ HttpStatus : " + HttpStatus.CREATED);
    }

    /**
     * Add Bank Account
     *
     * @param bankAccount bank Account object
     */
    @PutMapping(value = "/bankAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateBankAccount(@Valid @RequestBody BankAccount bankAccount) {

        // if user already exist send status and error message
        if (!bankAccountService.updateBankAccount(bankAccount)) {
            logger.error("PUT bankAccount -> " +
                    "updateBankAccount /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message : " +
                    " This user don't exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This bank account don't exist");
        }

        logger.info("PUT bankAccount -> updateBankAccount /**/ HttpStatus : " + HttpStatus.CREATED);
    }

    /**
     * Delete Bank Account
     *
     * @param bankAccount Bank Account record object
     */
    @DeleteMapping(value = "/bankAccount")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBankAccount(@Valid @RequestBody BankAccount bankAccount) {
        // if there is no Bank Account send status and error message
        if (!bankAccountService.deleteBankAccount(bankAccount)) {
            logger.error("DELETE bankAccount -> deleteBankAccount /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This bank account don't exist : '{}'.", bankAccount.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This bank Account don't " +
                    "exist");
        }
        logger.info("DELETE Bank Account -> deleteBankAccount /**/ HttpStatus : " + HttpStatus.OK);
    }


    /**
     * Get all bank accounts
     *
     * @return List of bank accounts
     */
    @GetMapping(value = "/bankAccounts")
    public List<BankAccount> getBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts();

        if (bankAccounts == null || bankAccounts.isEmpty()) {

            logger.error("GET bankAccounts -> getBankAccounts /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ " +
                    "Message : There is no bank Accounts in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no bank Accounts in" +
                    " the data base");
        }
        logger.info("GET bankAccounts -> getBankAccounts /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", bankAccounts.toString());

        return bankAccounts;
    }
}
