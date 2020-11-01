package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.TransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.Transfer;
import com.payMyBuddy.buddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    static final Logger logger = LogManager
            .getLogger(TransferServiceImpl.class);

    // initialize objects
    TransferDao transferDao;
    UserDao userDao;

    /**
     * Field injection of bank account dao
     *
     * @param userDao     user dao
     * @param transferDao transfer dao
     */
    @Autowired
    public void setTransferDao(UserDao userDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    @Override
    public boolean addTransfer(Transfer transfer) {
        Optional<User> user =
                userDao.getById(transfer.getFromUser().getId());

        Optional<User> userDestination =
                userDao.getById(transfer.getToUser().getId());
        // check if user exist
        if (!user.isPresent() && !userDestination.isPresent()) {
            logger.error("There is no users with this ids");
            throw new NoSuchElementException("There is no users with this ids");
        }

        // check if destination user exist in contact list
        if (!user.get().getContacts().contains(userDestination.get())) {
            logger.error("Destination user is not in your contact list");
            throw new NoSuchElementException("Destination user is not in your contact list");
        }

        //if amount is 0 send error
        if (transfer.getAmount() <= 0) {
            logger.error("Amount of transfer is wrong");
            throw new NoSuchElementException("Amount of transfer is wrong");
        }

        // if user don't have enough money
        if (user.get().getWallet() == null || (user.get().getWallet() - transfer.getAmount()) < 0) {
            logger.error("There is not enough money to do this transfer");
            throw new NoSuchElementException("There is not enough money to do this transfer");
        }

        // get amount of money of user to transfer
        user.get().setWallet(user.get().getWallet() - (transfer.getAmount() * Transfer.FEES_OF_TRANSFER+ transfer.getAmount() ));

        // if wallet null create it if not add amount
        if (userDestination.get().getWallet() != null) {
            userDestination.get().setWallet(userDestination.get().getWallet() + transfer.getAmount());
        } else {
            userDestination.get().setWallet(transfer.getAmount());
        }

        // save objects
        userDao.save(user.get());
        userDao.save(userDestination.get());
        transferDao.save(transfer);

        return true;
    }

    /**
     * Get all transfers
     *
     * @return list of  transfers
     */
    @Override
    public List<Transfer> getTransfers() {
        return transferDao.findAll();
    }

    /**
     * Get transfer by user id
     *
     * @param userId user id
     * @return list of transfers
     */
    @Override
    public List<Transfer> getTransfersByUserId(Integer userId) {
        if (userDao.existsById(userId)) {
            return transferDao.findAllByFromUserId(userId);
        }
        return null;
    }
}
