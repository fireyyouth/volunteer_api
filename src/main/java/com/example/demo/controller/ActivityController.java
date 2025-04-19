package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import com.example.demo.entity.MyUser;
import com.example.demo.entity.Activity;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<Activity> getActivities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);
        List<Activity> activities = activityRepository.getActivitiesByCreator(user.get());
        return activities;
    }
}
