package com.payMyBuddy.buddy.dao;


import com.payMyBuddy.buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Integer getIdByEmail(String email);

    User getByEmail(String email);
}