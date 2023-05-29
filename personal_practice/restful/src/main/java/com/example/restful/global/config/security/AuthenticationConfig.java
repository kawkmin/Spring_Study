package com.example.restful.global.config.security;

import com.example.restful.domain.jwtPractice.application.UserService;
import com.example.restful.global.config.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

  private final UserService userService;

  @Value("${jwt.secret}")
  private String secretKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .httpBasic().disable()
        .csrf().disable()
        .cors().and()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 사용하는 경우
        .and()
        .addFilterBefore(new JwtFilter(userService, secretKey),
            UsernamePasswordAuthenticationFilter.class)
        .build();

  }
}
