package com.example.demo.controller;

import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
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
import com.example.demo.repository.ApplyRepository;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import com.example.demo.entity.MyUser;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Apply;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public List<Apply> getAllActivities() {
        return applyRepository.findAll();
    }

    @GetMapping("/own")
    public List<Apply> getOwnActivities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);
        return applyRepository.getApplysByUser(user.get());
    }

    @GetMapping("/{id}")
    public Apply getApplyById(@PathVariable Long id) {
        return applyRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Apply updateStatus(@PathVariable Long id, @RequestParam(value = "status", required = true) String status) {
        Apply apply = applyRepository.findById(id).orElse(null);
        if (!apply.getStatus().equals("created")) {
            return apply;
        }
        apply.setStatus(status);
        applyRepository.save(apply);
        if (status.equals("approved")) {
            if (apply.getKind().equals("join")) {
                Activity activity = activityRepository.findById(apply.getActivity().getId()).orElse(null);
                activity.addParticipant(apply.getUser());
                activityRepository.save(activity);
            } else if (apply.getKind().equals("host")) {
                Activity activity = activityRepository.findById(apply.getActivity().getId()).orElse(null);
                activity.setStatus("approved");
                activityRepository.save(activity);
            }
        }
        return apply;
    }
    
    @PostMapping(value = "/", consumes = "application/json")
    public Apply createApply(@RequestBody Apply applyInfo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String account = auth.getName();
        Optional<MyUser> user = userRepository.findByAccount(account);
        Apply apply = new Apply();
        apply.setUser(user.get());
        apply.setActivity(activityRepository.findById(applyInfo.getActivity().getId()).orElse(null));
        apply.setKind(applyInfo.getKind());
        apply.setStatus("created");
        return applyRepository.save(apply);
    }

    @DeleteMapping("/{id}")
    public Apply deleteApply(@PathVariable Long id) {
        Apply apply = applyRepository.findById(id).orElse(null);
        applyRepository.delete(apply);
        return apply;
    }
}