package com.example.start;

import com.example.start.repository.MemberRepository;
import com.example.start.repository.MemoryMemberRepository;
import com.example.start.service.MemberService;
import hello.hellospring.repository.JdbcMemberRepository;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  private DataSource dataSource;

  @Autowired
  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    // return new MemoryMemberRepository();
    return new JdbcMemberRepository(dataSource);
  }
}
