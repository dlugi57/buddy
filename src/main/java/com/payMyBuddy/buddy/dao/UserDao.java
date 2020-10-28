package com.payMyBuddy.buddy.dao;


import com.payMyBuddy.buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Integer getIdByEmail(String email);

    Optional<User> getByEmail(String email);

    Optional<User> getById(Integer userId);

    //User getByEmail(String email);


    //User getById(Integer userId);
}