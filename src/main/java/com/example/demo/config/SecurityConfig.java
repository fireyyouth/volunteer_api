package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.MyUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailHandler customAuthenticationFailHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailHandler customAuthenticationFailHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailHandler = customAuthenticationFailHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuring HTTP security with SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity security = http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/login",  "/user/register").permitAll() // Allow unauthenticated access to these URLs
            .anyRequest().authenticated()  // Require authentication for other endpoints
        );
        security.csrf(csrf -> csrf.disable());
        security.formLogin(form -> form.successHandler(customAuthenticationSuccessHandler)
            .failureHandler(customAuthenticationFailHandler));
        security.logout(logout -> logout
            .logoutSuccessHandler(customLogoutSuccessHandler));
        return security.build();
    }
}
