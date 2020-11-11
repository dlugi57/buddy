package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankTransferDao extends JpaRepository<BankTransfer, Integer> {

    List<BankTransfer>  findAllByBankAccountId(Integer bankAccountId);

    Optional<BankTransfer> getById(Integer id);
}