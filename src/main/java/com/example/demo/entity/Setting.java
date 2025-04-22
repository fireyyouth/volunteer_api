package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Setting {
    @Id
    int id;

    int hostHourRequirement;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getHostHourRequirement() {
        return hostHourRequirement;
    }
    public void setHostHourRequirement(int hostHourRequirement) {
        this.hostHourRequirement = hostHourRequirement;
    }
}
