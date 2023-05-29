package com.example.restful.domain.jwtPractice.application;

import com.example.restful.global.config.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Value("${jwt.secret}")
  private String secretKey;

  private Long expiredMs = 1000 * 60 * 60l;

  public String login(String userName, String password) {
    // 로그인 인증 과정(아이디 비번 맞는지) 로직 생략
    return JwtUtil.createJwt(userName, secretKey, expiredMs);
  }
}
