package com.payMyBuddy.buddy.dao;

import com.payMyBuddy.buddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferDao extends JpaRepository<Transfer, Integer> {

    List<Transfer> findAllByFromUserId(Integer userId);

    Optional<Transfer> getById(Integer id);
}