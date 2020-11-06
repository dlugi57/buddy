package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.ContactsDao;
import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ContactsServiceTest {

    @TestConfiguration
    static class ContactsServiceImplTestContextConfiguration {
        @Bean
        public ContactsService contactsService() {
            return new ContactsServiceImpl();
        }
    }

    @MockBean
    ContactsDao contactsDao;

    @Resource
    ContactsService contactsService;

    static Date today = new Date();

    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static User contact = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static Contacts contacts = new Contacts(user, contact);


    @Test
    void addContact() {
        // GIVEN
        user.setId(1);
        contact.setId(1);
        given(contactsDao.save(any(Contacts.class))).willReturn(contacts);
        // WHEN
        boolean contactsTest = contactsService.addContact(new Contacts(user, contact));
        // THEN
        verify(contactsDao, times(1)).save(any(Contacts.class));

        assertThat(contactsTest).isEqualTo(true);
    }


    public static List<Contacts> contactsList = new ArrayList<>();

    static {
        contactsList.add(contacts);
        contactsList.add(contacts);
        contactsList.add(contacts);
    }

    @Test
    void getContacts() {
        given(contactsDao.findAll()).willReturn(contactsList);
        // WHEN
        List<Contacts> contactsListTest = contactsService.getContacts();
        // THEN
        verify(contactsDao, times(1)).findAll();
        assertThat(contactsListTest.size()).isEqualTo(3);
    }

    @Test
    void getContactsByUserId() {
        user.setId(1);
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);
        // WHEN
        List<Contacts> contactsListTest = contactsService.getContactsByUserId(1);
        // THEN
        verify(contactsDao, times(1)).findAllByUserId(anyInt());
        assertThat(contactsListTest.size()).isEqualTo(3);
    }

    @Test
    void getContactsByUserId_Null() {
        user.setId(1);
        given(contactsDao.findAllByUserId(anyInt())).willReturn(null);
        // WHEN
        List<Contacts> contactsListTest = contactsService.getContactsByUserId(1);
        // THEN
        verify(contactsDao, times(1)).findAllByUserId(anyInt());
        assertThat(contactsListTest).isEqualTo(null);
    }

    @Test
    void deleteContact() {
        user.setId(1);
        contact.setId(1);
        given(contactsDao.findAllByUserId(anyInt())).willReturn(contactsList);

        // WHEN
        boolean contactsTest = contactsService.deleteContact(contacts);
        // THEN
        verify(contactsDao, times(1)).findAllByUserId(anyInt());
        verify(contactsDao, times(1)).delete(any(Contacts.class));

        assertThat(contactsTest).isEqualTo(true);

    }

    @Test
    void deleteContact_Null() {
        user.setId(1);
        contact.setId(1);
        given(contactsDao.findAllByUserId(anyInt())).willReturn(Collections.emptyList());

        // WHEN
        boolean contactsTest = contactsService.deleteContact(contacts);
        // THEN
        verify(contactsDao, times(1)).findAllByUserId(anyInt());

        assertThat(contactsTest).isEqualTo(false);

    }
}