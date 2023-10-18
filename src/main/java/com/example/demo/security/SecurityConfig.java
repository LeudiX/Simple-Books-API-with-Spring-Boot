package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ! Defining my own Custom Security Configuration 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
        expressionInterceptUrlRegistry
        .anyRequest()  //proccessing all defined requests
        .permitAll())  //allowing unrestricted access to all endpoints
        .csrf(AbstractHttpConfigurer::disable); //enabling CSRF protection
        return http.build(); //building the http object
    }
}