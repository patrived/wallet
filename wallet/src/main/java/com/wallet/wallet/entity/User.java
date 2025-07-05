package com.wallet.wallet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserWallet getWallet() {
        return wallet;
    }

    public void setWallet(UserWallet wallet) {
        this.wallet = wallet;
    }

    public User() {
    }

    public User(Long id, String name, String email, UserWallet wallet) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.wallet = wallet;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private UserWallet wallet;


}
