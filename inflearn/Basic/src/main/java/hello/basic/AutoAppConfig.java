package hello.basic;

import hello.basic.member.MemberRepository;
import hello.basic.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "hello.basic.member", //default = @ComponentScan의 패키지 위치
    basePackageClasses = AutoAppConfig.class, // default = @ComponentScan의 클래스
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
    // APPConfig의 @configuration은 @component가 있으니, 제외 해주자
    // 그 외에도 @Controller @Sevice 등 더 있다.
)
public class AutoAppConfig {

  @Bean
    // @Bean(name = "memoryMemberRepository")
    // 자동으로 생성되는 memoryMemberRepository Bean의 이름이 같지만, 수동이 우선권을 가짐
  MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
}
