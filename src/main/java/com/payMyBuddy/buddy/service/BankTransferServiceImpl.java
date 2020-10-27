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
     */
    @Autowired
    public void setBankTransferDao(BankAccountDao bankAccountDao, UserDao userDao, BankTransferDao bankTransferDao) {
        this.bankAccountDao = bankAccountDao;
        this.userDao = userDao;
        this.bankTransferDao = bankTransferDao;
    }


    @Override
    public Integer addBankTransfer(BankTransfer bankTransfer) {

        Optional<BankAccount> bankAccount =
                bankAccountDao.getById(bankTransfer.getBankAccount().getId());

        if (!bankAccount.isPresent()) {

            logger.error("There is no bank account with this id");
            throw new NoSuchElementException("There is no bank account with this id");
        }

        User user = userDao.getById(bankAccount.get().getUser().getId());

        if (bankTransfer.getAmount() == 0) {
            logger.error("Amount of transfer is wrong");
            throw new NoSuchElementException("Amount of transfer is wrong");
        }

        if (bankTransfer.getAmount() < 0){

        }

        // TODO: 28/10/2020 make this easier with minus  
        if (bankTransfer.getTransferType().equals(TransferType.INCOMING)){
            if (user.getWallet() != null) {
                user.setWallet(user.getWallet() + bankTransfer.getAmount() * BankTransfer.FEES_OF_TRANSFER);

            } else {
                user.setWallet(bankTransfer.getAmount() * BankTransfer.FEES_OF_TRANSFER);
            }
        }else if (bankTransfer.getTransferType().equals(TransferType.EXITING)){
            
        }else{
            logger.error("Unknown transfer type");
            throw new NoSuchElementException("Unknown transfer type");
        }
            
        



        userDao.save(user);

        //bankTransferDao.save(bankTransfer).getId();
// TODO: 28/10/2020 when i save bank transfer the enum type is 0 even if is excacly the same
        return bankTransferDao.save(bankTransfer).getId();
    }
}
