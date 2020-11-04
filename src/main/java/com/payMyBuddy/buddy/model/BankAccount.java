package com.payMyBuddy.buddy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Proxy(lazy = false)
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String number;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public BankAccount() {
    }

    public BankAccount(@NotNull String number, @NotNull String name, User user) {
        this.number = number;
        this.name = name;
        this.user = user;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
