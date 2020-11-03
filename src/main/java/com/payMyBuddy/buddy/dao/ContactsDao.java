package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.Contacts;
import com.payMyBuddy.buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsDao extends JpaRepository<Contacts, Integer> {
}
