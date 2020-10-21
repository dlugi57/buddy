package com.payMyBuddy.buddy.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transfer {

    @Id
    @GeneratedValue
    private Integer id;

    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;

    private LocalDate creationDate;

    private String description;



}
