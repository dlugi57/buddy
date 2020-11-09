package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.ContactsDao;
import com.payMyBuddy.buddy.dao.TransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.Transfer;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class TransferServiceImplTest {

    @TestConfiguration
    static class TransferServiceImplTestContextConfiguration {
        @Bean
        public TransferService transferService() {
            return new TransferServiceImpl();
        }
    }

    @Resource
    TransferService transferService;

    @MockBean
    TransferDao transferDao;

    @MockBean
    UserDao userDao;

    @MockBean
    ContactsDao contactsDao;

    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static User contact = new User("John", "Boyd", "jaboyd1@email.com", "password", today);
    public static Transfer transfer = new Transfer(1000.0, user, contact, today, "description");
    public static Contacts contacts = new Contacts(user, contact);
    public static List<Contacts> contactsList = new ArrayList<>();

    static {
        contactsList.add(contacts);
        contactsList.add(contacts);
        contactsList.add(contacts);
    }

    @Test
    void addTransfer() {

        // GIVEN
        transfer.setId(1);
        transfer.setAmount(1000.0);
        user.setId(1);
        user.setWallet(1000.0);
        contact.setId(2);


        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);
        given(transferDao.save(any(Transfer.class))).willReturn(transfer);

        // WHEN
        boolean transferTest = transferService.addTransfer(new Transfer(1000.0, user, contact, today,
                "description"));

        // THEN
        verify(userDao, times(2)).getById(anyInt());
        verify(userDao, times(2)).save(any(User.class));
        verify(transferDao, times(1)).save(any(Transfer.class));
        assertThat(transferTest).isEqualTo(true);
    }

    @Test
    void addTransfer_InvalidUser() {

        // GIVEN
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);
        given(transferDao.save(any(Transfer.class))).willReturn(transfer);

        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> transferService.addTransfer(new Transfer(1000.0, user, contact, today,
                        "description")));
    }

    @Test
    void addTransfer_InvalidWallet() {
        user.setId(1);
        contact.setId(2);

        // GIVEN
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);
        given(transferDao.save(any(Transfer.class))).willReturn(transfer);

        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> transferService.addTransfer(new Transfer(1000.0, user, contact, today,
                        "description")));
    }

    @Test
    void addTransfer_InvalidContact() {
        user.setId(1);
        contact.setId(2);

        // GIVEN
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(contactsDao.findAllByUserId(anyInt())).willReturn(Collections.emptyList());
        given(transferDao.save(any(Transfer.class))).willReturn(transfer);

        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> transferService.addTransfer(new Transfer(1000.0, user, contact, today,
                        "description")));
    }


    @Test
    void addTransfer_InvalidValue() {
        user.setId(1);
        contact.setId(2);
        transfer.setAmount(0.0);

        // GIVEN
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);
        given(transferDao.save(any(Transfer.class))).willReturn(transfer);

        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> transferService.addTransfer(new Transfer(1000.0, user, contact, today,
                        "description")));
    }

    public static List<Transfer> transfers = new ArrayList<>();

    static {
        transfers.add(transfer);
        transfers.add(transfer);
        transfers.add(transfer);
    }

    @Test
    void getTransfers() {
        // GIVEN
        given(transferDao.findAll()).willReturn(transfers);
        // WHEN
        List<Transfer> transfersAll = transferService.getTransfers();
        // THEN
        verify(transferDao, times(1)).findAll();
        assertThat(transfersAll.size()).isEqualTo(3);
    }

    @Test
    void getTransfersByUserId() {

        // GIVEN
        user.setId(1);

        given(userDao.existsById(anyInt())).willReturn(true);
        given(transferDao.findAllByFromUserId(anyInt())).willReturn(transfers);
        // WHEN
        List<Transfer> transfersAll = transferService.getTransfersByUserId(1);
        // THEN
        verify(transferDao, times(1)).findAllByFromUserId(anyInt());

        assertThat(transfersAll.size()).isEqualTo(3);

    }


    @Test
    void getTransfersByUserId_InvalidUser() {

        // GIVEN
        given(userDao.existsById(anyInt())).willReturn(false);
        given(transferDao.findAllByFromUserId(anyInt())).willReturn(transfers);
        // WHEN
        List<Transfer> transfersAll = transferService.getTransfersByUserId(1);
        // THEN

        assertThat(transfersAll).isEqualTo(null);

    }
}