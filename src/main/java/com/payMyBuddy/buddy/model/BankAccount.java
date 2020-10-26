package com.payMyBuddy.buddy.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Proxy(lazy = false)
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String number;

    @NotNull
    private String name;

    // TODO: 26/10/2020 how to make not null only for id and should I add ,cascade = CascadeType.MERGE
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
