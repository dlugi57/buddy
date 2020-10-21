package com.payMyBuddy.buddy.model;

import javax.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue
    private Integer id;

    private String number;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
