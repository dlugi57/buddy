package com.payMyBuddy.buddy.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;

    private String lastName;
    @Email
    private String email;

    private String password;

    private Double wallet;
    @OneToMany
    private List<BankAccount> bankAccounts;
    @ManyToMany
    private List<User> contacts;

    private LocalDate creationDate;


}
