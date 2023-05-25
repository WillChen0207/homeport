package com.homeport.app.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Bounty")
@Table(name = "bounty")
public class Bounty {
    @Id
    @Column(name = "user_id", nullable = false, length = 11)
    private String user_id;

    @Column(name = "balance", nullable = false)
    private Double balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}