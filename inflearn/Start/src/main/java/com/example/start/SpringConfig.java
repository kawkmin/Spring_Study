package com.example.start;

import com.example.start.repository.MemberRepository;
import com.example.start.repository.MemoryMemberRepository;
import com.example.start.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  @Bean
  public MemberService memberService(){
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository(){
    return new MemoryMemberRepository();
  }
}
