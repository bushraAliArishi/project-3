package com.example.bankmanagementsystem.config;

import com.example.bankmanagementsystem.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                // Publicly accessible endpoints
                .requestMatchers(
                        "/api/v1/user/login",
                        "/api/v1/customer/register",
                        "/api/v1/employee/register"
                ).permitAll()
                // Admin-only endpoints
                .requestMatchers(
                        "/api/v1/user/all-users",
                        "/api/v1/user/{id}",
                        "/api/v1/user/delete/**",
                        "/api/v1/account/all-accounts"
                ).hasAuthority("ADMIN")
                // Employee-only endpoints
                .requestMatchers(
                        "/api/v1/account/activate/**",
                        "/api/v1/account/block/**"
                ).hasAuthority("EMPLOYEE")
                // Customer-only endpoints
                .requestMatchers(
                        "/api/v1/account/create",
                        "/api/v1/account/deposit",
                        "/api/v1/account/withdraw",
                        "/api/v1/account/transfer/**",
                        "/api/v1/account/{accountNumber}"
                ).hasAuthority("CUSTOMER")
                // Any other request
                .anyRequest().authenticated()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return http.build();
    }
}
