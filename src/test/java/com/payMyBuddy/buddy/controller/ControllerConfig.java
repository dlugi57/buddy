package com.payMyBuddy.buddy.controller;

import com.payMyBuddy.buddy.dao.*;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ControllerConfig {
    @MockBean
    UserDao userDao;

    @MockBean
    BankTransferDao bankTransferDao;

    @MockBean
    BankAccountDao bankAccountDao;

    @MockBean
    TransferDao transferDao;

    @MockBean
    ContactsDao contactsDao;
}
