package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Activity;
import com.example.demo.entity.MyUser;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    public List<Activity> getActivitiesByCreator(MyUser creator);
}
