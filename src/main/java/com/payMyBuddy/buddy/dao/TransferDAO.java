package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDAO extends JpaRepository<Transfer, Integer> {

}