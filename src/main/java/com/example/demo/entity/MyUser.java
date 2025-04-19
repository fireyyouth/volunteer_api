package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.time.Instant;
import java.time.LocalDate;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    @Column(unique = true)
    private String account;

    private String password;
    private String name;
    private String gender;
    private String email;
    
    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIdentityReference(alwaysAsId = true)
    List<Activity> activities;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Activity> joinedActivities = new HashSet<>();

    
    @JsonProperty("volunteerHour")
    public int getVolunteerHour() {
        int total = 0;
        for (Activity activity : joinedActivities) {
            // 审批通过且活动结束
            if (activity.getStatus().equals("approved") && activity.getEndDate().isBefore(LocalDate.now())) {
                total += activity.getVolunteerHour();
            }
        }
        return total;
    }

    public void joinActivity(Activity activity) {
        joinedActivities.add(activity);
        activity.getParticipants().add(this);
    }
    public void leaveActivity(Activity activity) {
        joinedActivities.remove(activity);
        activity.getParticipants().remove(this);
    }
    
    // Getters and Setters
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Activity> getActivities() {
        return activities;
    }
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    public Set<Activity> getJoinedActivities() {
        return joinedActivities;
    }
    public void setJoinedActivities(Set<Activity> joinedActivities) {
        this.joinedActivities = joinedActivities;
    }
}
