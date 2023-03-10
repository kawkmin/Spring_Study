package com.example.start;

import com.example.start.Aop.TimeTraceAop;
import com.example.start.repository.JdbcTemplateMemberRepository;
import com.example.start.repository.JpaMemberRepository;
import com.example.start.repository.MemberRepository;
import com.example.start.repository.MemoryMemberRepository;
import com.example.start.service.MemberService;
import hello.hellospring.repository.JdbcMemberRepository;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  private final MemberRepository memberRepository;

  @Autowired
  public SpringConfig(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository);
  }

//  @Bean
//  public TimeTraceAop timeTraceAop() {
//    return new TimeTraceAop();
//  }

//  @Bean
//  public MemberRepository memberRepository() {
// return new MemoryMemberRepository();
// return new JdbcMemberRepository(dataSource);
// return new JdbcTemplateMemberRepository(dataSource);
//    return new JpaMemberRepository(em);
//  }
}