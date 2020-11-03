package com.payMyBuddy.buddy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@Entity
@Proxy(lazy = false)
public class BankTransfer implements Serializable {

    public static final double FEES_OF_TRANSFER = 0.995;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;

    private TransferType transferType;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private String description;

    public Date getCreationDate() {
        return (Date)creationDate.clone();
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = (Date)creationDate.clone();
    }

}
