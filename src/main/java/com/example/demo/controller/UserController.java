package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.entity.MyUser;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("")
    public MyUser createUser(@RequestBody MyUser user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public MyUser getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public MyUser updateUser(@PathVariable Long id, @RequestBody MyUser user) {
        MyUser existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            existingUser.setGender(user.getGender());
        }
        return userRepository.save(existingUser);
    }

    @PutMapping(value = "/{id}/password", consumes = "application/json")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody PasswordRequest passwordRequest) {
        MyUser existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            return ResponseEntity.ok(userRepository.save(existingUser));
        } else {
            return ResponseEntity.badRequest().body(Map.of("detail", "旧密码不正确"));
        }
    }
}