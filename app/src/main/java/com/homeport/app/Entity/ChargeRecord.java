package com.homeport.app.Entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "ChargeRecord")
@Table(name = "charge_record")
public class ChargeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Integer record_id;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public ChargeRecord(String user_id, Double amount, Instant timestamp) {
        this.user_id = user_id;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public ChargeRecord() {

    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRecordId() {
        return record_id;
    }

    public void setRecordId(Integer record_id) {
        this.record_id = record_id;
    }
}