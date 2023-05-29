package com.example.restful.domain.jwtPractice.api;

import com.example.restful.domain.jwtPractice.application.UserService;
import com.example.restful.domain.jwtPractice.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
    return ResponseEntity.ok(userService.login(dto.getUserName(), ""));
  }
}
