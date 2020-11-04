package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.config.ContactsId;
import com.payMyBuddy.buddy.dao.ContactsDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    /**
     * Add contact
     *
     * @param contact contact object
     * @return true when success
     */
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
     * Get all contacts
     *
     * @return List of all contacts
     */
    @Override
    public List<Contacts> getContacts() {
        return contactsDao.findAll();
    }

    /**
     * Get contacts by user id
     *
     * @return List of contacts
     */
    @Override
    public List<Contacts> getContactsByUserId(Integer userId) {

        return contactsDao.findAllByUserId(userId);
    }

    /**
     * Delete contact
     *
     * @param contact contact object
     * @return true when success
     */
    @Override
    public boolean deleteContact(Contacts contact) {
        // TODO: 04/11/2020 is better way to do this?
        List<Contacts> contacts = getContactsByUserId(contact.getUser().getId());
        for (Contacts contactCheck: contacts){
            if (contactCheck.getContact().getId().equals(contact.getContact().getId()) ){
                try {
                    contactsDao.delete(contact);

                    return true;

                } catch (Exception e) {
                    logger.info(e.toString());
                }
            }
        }

        return false;
        }

}
