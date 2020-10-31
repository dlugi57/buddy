package com.payMyBuddy.buddy.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {
   // private static final long serialVersionUID = 5727638570064420003L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    private Double wallet;

    //@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> contacts;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


}
