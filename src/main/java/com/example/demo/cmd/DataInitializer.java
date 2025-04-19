package com.example.demo.cmd;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.entity.Activity;
import com.example.demo.entity.MyUser;
import com.example.demo.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.repository.ActivityRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        // Create the admin user
        MyUser admin = new MyUser();
        admin.setAccount("admin");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(adminRole);
        admin.getRoles().add(userRole);
        userRepository.save(admin);

        // create user01 user
        MyUser user01 = new MyUser();
        user01.setAccount("frank");
        user01.setName("frank");
        user01.setGender("male");
        user01.setEmail("frank@foo.com");
        user01.setPassword(passwordEncoder.encode("frank"));
        user01.getRoles().add(userRole);
        userRepository.save(user01);

        // Create the activity
        Activity activity = new Activity();
        activity.setTitle("Activity 1");
        activity.setCreator(user01);
        activityRepository.save(activity);
    }
}