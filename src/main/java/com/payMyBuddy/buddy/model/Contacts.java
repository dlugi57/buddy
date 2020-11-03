package com.payMyBuddy.buddy.model;

import com.payMyBuddy.buddy.config.ContactsId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(ContactsId.class)
public class Contacts implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User contact;
}
