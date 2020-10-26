package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.controller.UserController;
import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bank Account data manipulation
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    static final Logger logger = LogManager
            .getLogger(BankAccountServiceImpl.class);

    // initialize objects
    BankAccountDao bankAccountDao;

    /**
     * Field injection of bank account dao
     *
     * @param bankAccountDao bank account dao
     */
    @Autowired
    public void setBankAccountDao(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }


    /**
     * Add bank account
     *
     * @param bankAccount bank account object
     * @return true when success
     */
    @Override
    public boolean addBankAccount(BankAccount bankAccount) {
        try {
            if (bankAccountDao.save(bankAccount).getId() > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }

        return false;
    }

    /**
     * Update bank account
     *
     * @param bankAccount bank account object
     * @return true when success
     */
    @Override
    public boolean updateBankAccount(BankAccount bankAccount) {
        // TODO: 24/10/2020 how to do this in proper way?
        if (bankAccountDao.existsByNumber(bankAccount.getNumber())) {
            try {

                if (bankAccountDao.save(bankAccount).getId() > 0) {
                    return true;
                }
            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
        return false;
    }

    /**
     * Delete bank account
     *
     * @param bankAccount bank account object
     * @return true when success
     */
    @Override
    public boolean deleteBankAccount(BankAccount bankAccount) {
        if (bankAccountDao.existsById(bankAccount.getId())) {
            try {
                bankAccountDao.delete(bankAccount);

                return true;

            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
        return false;
    }

    /**
     * Get all bank accounts
     *
     * @return list of bank accounts
     */
    @Override
    public List<BankAccount> getBankAccounts() {
        return bankAccountDao.findAll();
    }
}
