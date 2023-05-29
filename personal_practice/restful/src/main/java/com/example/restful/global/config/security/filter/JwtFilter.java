package com.example.restful.global.config.security.filter;

import com.example.restful.domain.jwtPractice.application.UserService;
import com.example.restful.global.config.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    log.info("authorization {}", authorization);

    // token 안보내면 Block
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      log.error("authorization이 없습니다");
      filterChain.doFilter(request, response);
      return;
    }

    //token 꺼내기
    String token = authorization.split(" ")[1];

    // token Expired 여부
    if (JwtUtil.isExpired(token, secretKey)) {
      log.error("토큰 만료");
      filterChain.doFilter(request, response);
      return;
    }

    String userName = JwtUtil.getUserName(token, secretKey);
    log.info("userName ={}", userName);

    //권한 부여
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userName, null, List.of(new SimpleGrantedAuthority("USER")));

    //Detail 넣기
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }
}
