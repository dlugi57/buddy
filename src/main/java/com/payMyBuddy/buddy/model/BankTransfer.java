package com.payMyBuddy.buddy.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BankTransfer {

    @Id
    @GeneratedValue
    private Integer id;

    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;

    private TransferType transferType;

    private LocalDate creationDate;

    private String description;
}
