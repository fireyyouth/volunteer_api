package com.example.demo.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kind;

    @ManyToOne
    private MyUser user;

    @ManyToOne
    private Activity activity;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }

    public MyUser getUser() {
        return user;
    }
    public void setUser(MyUser user) {
        this.user = user;
    }
    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public Instant getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }
    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}