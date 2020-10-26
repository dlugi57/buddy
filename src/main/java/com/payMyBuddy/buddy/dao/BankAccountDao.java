package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, Integer> {

    boolean existsByNumber(String number);
}