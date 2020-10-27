package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, Integer> {

    boolean existsByNumber(String number);

    List<BankAccount> findAllByUserId(Integer userId);

    Optional<BankAccount> getById(Integer id);
}