package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import com.example.demo.entity.MyUser;
import com.example.demo.entity.Role;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Your JPA repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByAccount(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = User.builder()
            .username(user.getAccount())
            .password(user.getPassword()) // should already be encoded
            .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new)) // or use authorities
            .build();

        System.out.printf("User loaded: %s, password: %s\r\n", user.getAccount(), user.getPassword());
        return userDetails;
    }
}