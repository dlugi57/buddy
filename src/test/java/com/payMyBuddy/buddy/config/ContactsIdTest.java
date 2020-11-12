package com.payMyBuddy.buddy.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactsIdTest {

    @Test
    void testEquals() {

        ContactsId x = new ContactsId();  // equals and hashCode check name field value
        x.setContact(1);
        x.setUser(2);

        ContactsId y = new ContactsId();

        y.setContact(x.getContact());
        y.setUser(x.getUser());
        assertTrue(x.equals(y) && y.equals(x));
        assertEquals(x.hashCode(), y.hashCode());
    }
}