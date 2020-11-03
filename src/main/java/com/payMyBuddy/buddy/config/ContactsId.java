package com.payMyBuddy.buddy.config;

import com.payMyBuddy.buddy.model.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class serves to create composite primary key for contacts
 */
public class ContactsId implements Serializable {
    private User user;

    private User contact;

    public ContactsId() {
    }

    public ContactsId(User user, User contact) {
        this.user = user;
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactsId that = (ContactsId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, contact);
    }
}
