package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.config.ContactsId;
import com.payMyBuddy.buddy.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsDao extends JpaRepository<Contacts, ContactsId> {
    boolean existsByUserId(Integer id);

    List<Contacts> findAllByUserId(Integer userId);

    List<Contacts> findContactsByUserId(Integer userId);
}
