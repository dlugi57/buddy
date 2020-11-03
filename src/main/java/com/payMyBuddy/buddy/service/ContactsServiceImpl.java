package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.ContactsDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contacts data manipulation
 */
@Service
public class ContactsServiceImpl implements ContactsService {
    static final Logger logger = LogManager
            .getLogger(ContactsServiceImpl.class);

    // initialize objects
    ContactsDao contactsDao;

    /**
     * Field injection of contacts dao
     *
     * @param contactsDao contacts dao
     */
    @Autowired
    public void setUserDao(ContactsDao contactsDao) {
        this.contactsDao = contactsDao;
    }
    @Override
    public boolean addContact(Contacts contact) {
        try {
            contactsDao.save(contact);
            return true;
        } catch (Exception e) {
            logger.info(e.toString());
        }

        return false;

    }
    /**
     * Get all users
     *
     * @return List of all users
     */

    @Override
    public List<Contacts> getContacts() {
        return contactsDao.findAll();
    }
}
