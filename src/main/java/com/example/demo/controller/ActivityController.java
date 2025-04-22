package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import java.util.Set;

import com.example.demo.entity.MyUser;
import com.example.demo.dto.ErrorDetail;
import com.example.demo.entity.Activity;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @GetMapping("/own")
    public Set<Activity> getActivities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);
        return user.get().getActivities();
    }

    @GetMapping("/joined")
    public Set<Activity> getJoinedActivities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);
        return user.get().getJoinedActivities();
    }
    
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public ResponseEntity<?> createActivity(@RequestBody Activity activityInfo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);

        Activity activity = new Activity();
        activity.setCreator(user.get());
        activity.setTitle(activityInfo.getTitle());
        activity.setVolunteerCriteria(activityInfo.getVolunteerCriteria());
        activity.setVolunteerHour(activityInfo.getVolunteerHour());
        activity.setStartDate(activityInfo.getStartDate());
        activity.setEndDate(activityInfo.getEndDate());
        activity.setImageURL(activityInfo.getImageURL());
        try {
            return ResponseEntity.ok(activityRepository.save(activity));
        } catch(org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ErrorDetail("活动标题已存在"));
        }
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Long id, @RequestBody Activity activityInfo) {
        Activity activity = activityRepository.findById(id).orElse(null);
        if (activityInfo.getTitle() != null) {
            activity.setTitle(activityInfo.getTitle());
        }
        if (activityInfo.getVolunteerCriteria() != null) {
            activity.setVolunteerCriteria(activityInfo.getVolunteerCriteria());
        }
        if (activityInfo.getVolunteerHour() > 0) {
            activity.setVolunteerHour(activityInfo.getVolunteerHour());
        }
        if (activityInfo.getStartDate() != null) {
            activity.setStartDate(activityInfo.getStartDate());
        }
        if (activityInfo.getEndDate() != null) {
            activity.setEndDate(activityInfo.getEndDate());
        }
        if (activityInfo.getImageURL() != null) {
            activity.setImageURL(activityInfo.getImageURL());
        }
        if (activityInfo.getStatus() != null) {
            activity.setStatus(activityInfo.getStatus());
        }
        return activityRepository.save(activity);
    }

    @PostMapping("/{id}/leave")
    public Activity leaveActivity(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);

        Activity activity = activityRepository.findById(id).orElse(null);
        activity.removeParticipant(user.get());
        activityRepository.save(activity);
        return activity;
    }

    @DeleteMapping("/{id}")
    public Activity deleteActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id).orElse(null);
        activityRepository.delete(activity);
        return activity;
    }
}
