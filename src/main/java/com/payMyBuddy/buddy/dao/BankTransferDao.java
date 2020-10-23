package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferDao extends JpaRepository<BankTransfer, Integer> {

}