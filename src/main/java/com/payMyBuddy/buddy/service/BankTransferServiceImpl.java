package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.BankTransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.TransferType;
import com.payMyBuddy.buddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BankTransferServiceImpl implements BankTransferService {

    static final Logger logger = LogManager
            .getLogger(BankTransferServiceImpl.class);

    // initialize objects
    BankTransferDao bankTransferDao;
    BankAccountDao bankAccountDao;
    UserDao userDao;

    /**
     * Field injection of bank account dao
     *
     * @param bankAccountDao bank account dao
     * @param userDao user dao
     * @param bankTransferDao bank transfer dao
     */
    @Autowired
    public void setBankTransferDao(BankAccountDao bankAccountDao, UserDao userDao, BankTransferDao bankTransferDao) {
        this.bankAccountDao = bankAccountDao;
        this.userDao = userDao;
        this.bankTransferDao = bankTransferDao;
    }

    /**
     * Make bank transfer
     * When amount is positive the transfer is incoming when is negative the transfer is exiting
     *
     * @param bankTransfer bank transfer object
     * @return true if success
     */
    @Override
    public boolean addBankTransfer(BankTransfer bankTransfer) {

        Optional<BankAccount> bankAccount =
                bankAccountDao.getById(bankTransfer.getBankAccount().getId());
        // check if bank transfer exist
        if (!bankAccount.isPresent()) {

            logger.error("There is no bank account with this id");
            throw new NoSuchElementException("There is no bank account with this id");
        }

        Optional<User>  user = userDao.getById(bankAccount.get().getUser().getId());
        //if amount is 0 send error
        if (bankTransfer.getAmount() == 0) {
            logger.error("Amount of transfer is wrong");
            throw new NoSuchElementException("Amount of transfer is wrong");
        }
        // when is negative exiting transfer
        if (bankTransfer.getAmount() < 0) {

            if (user.get().getWallet() == null || (user.get().getWallet() + bankTransfer.getAmount()) < 0) {
                logger.error("There is not enough money to do this transfer");
                throw new NoSuchElementException("There is not enough money to do this transfer");
            } else {
                user.get().setWallet(user.get().getWallet() + bankTransfer.getAmount() * BankTransfer.FEES_OF_TRANSFER);
            }

            bankTransfer.setTransferType(TransferType.EXITING);
        }
        // incoming transfer
        else {
            // when wallet is null just add amount
            if (user.get().getWallet() != null) {
                user.get().setWallet(user.get().getWallet() + bankTransfer.getAmount() * BankTransfer.FEES_OF_TRANSFER);
            } else {
                user.get().setWallet(bankTransfer.getAmount() * BankTransfer.FEES_OF_TRANSFER);
            }
            // set type
            bankTransfer.setTransferType(TransferType.INCOMING);
        }
        // save objects
        userDao.save(user.get());
        bankTransferDao.save(bankTransfer);

        return true;
    }

    /**
     * Get all bank transfers
     *
     * @return list of bank transfers
     */
    @Override
    public List<BankTransfer> getBankTransfers() {
        return bankTransferDao.findAll();
    }


    /**
     * Get bank transfer  by user id
     *
     * @param userId user id
     * @return list of bank transfers
     */
    @Override
    public List<BankTransfer> getBankTransferByUserId(Integer userId) {
        List<BankTransfer> bankTransfers = new ArrayList<>();
        // check if user exist
        if (userDao.existsById(userId)) {
            // get all bank accounts
            List<BankAccount> bankAccounts = bankAccountDao.findAllByUserId(userId);
            if (bankAccounts != null && !bankAccounts.isEmpty()) {
                // get all bank transfers by bank accounts id
                for (BankAccount bankAccount : bankAccounts) {
                    bankTransfers.addAll(bankTransferDao.findAllByBankAccountId(bankAccount.getId()));
                }
                return bankTransfers;
            }
        }
        return null;
    }


}
