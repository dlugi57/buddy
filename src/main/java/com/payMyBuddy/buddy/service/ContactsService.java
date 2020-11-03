package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.Contacts;

import java.util.List;

public interface ContactsService {

    boolean addContact(Contacts contact);

    List<Contacts> getContacts();

    List<Contacts> getContactsByUserId(Integer userId);

    boolean deleteContact(Contacts contact);
}
