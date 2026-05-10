package com.mh.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable() //it disabled for the rest api
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()//All requests are public
                );

//it is also working i think
//        http
//                .cors().and()
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/uploads/**").permitAll() // ✅ allow access to uploaded images
//                        .requestMatchers("/api/**").permitAll()     // ✅ allow your APIs
//                        .anyRequest().authenticated()
//                );


        return http.build(); //“Take all the rules I’ve defined in HttpSecurity and create the chain of filters that will process each request.”
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
