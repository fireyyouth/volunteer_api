package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;
import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageURL;
    private LocalDate startDate;
    private LocalDate endDate;
    private String volunteerCriteria;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MyUser creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "activity_participants",
                joinColumns = @JoinColumn(name = "activity_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIdentityReference(alwaysAsId = true)
    private Set<MyUser> participants = new HashSet<>();

    private String status = "待审核";

    private int volunteerHour = -1;

    
    @JsonProperty("creatorName")
    public String getCreatorName() {
        return creator != null ? creator.getName() : null;
    }

    public void addParticipant(MyUser user) {
        participants.add(user);
        user.getJoinedActivities().add(this);
    }

    public void removeParticipant(MyUser user) {
        participants.remove(user);
        user.getJoinedActivities().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyUser getCreator() {
        return creator;
    }

    public void setCreator(MyUser creator) {
        this.creator = creator;
        creator.getActivities().add(this);
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getVolunteerCriteria() {
        return volunteerCriteria;
    }

    public void setVolunteerCriteria(String volunteerCriteria) {
        this.volunteerCriteria = volunteerCriteria;
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

    public Set<MyUser> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<MyUser> participants) {
        this.participants = participants;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getVolunteerHour() {
        return volunteerHour;
    }
    public void setVolunteerHour(int volunteerHour) {
        this.volunteerHour = volunteerHour;
    }
}
