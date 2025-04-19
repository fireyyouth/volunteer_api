package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import com.example.demo.entity.MyUser;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public CustomAuthenticationSuccessHandler(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    // You can send a custom response, like a JSON message
        UserDetails user = (UserDetails)authentication.getPrincipal();

        Optional<MyUser> myUser = userRepository.findByAccount(user.getUsername());

        // Convert UserDetails to JSON using Jackson
        HashMap<String, Object> r = new HashMap<String, Object>();
        r.put("detail", "登录成功");
        r.put("data", myUser.get());
        String json = objectMapper.writeValueAsString(r);

        // Send the JSON response
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}