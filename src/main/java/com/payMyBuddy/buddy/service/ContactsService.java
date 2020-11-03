package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.Contacts;

import java.util.List;

public interface ContactsService {

    boolean addContact(Contacts contact);

    List<Contacts> getContacts();
}
