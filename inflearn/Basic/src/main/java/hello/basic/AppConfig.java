package hello.basic;

import hello.basic.discount.DiscountPolicy;
import hello.basic.discount.FixDiscountPolicy;
import hello.basic.discount.RateDiscountPolicy;
import hello.basic.member.MemberService;
import hello.basic.member.MemberServiceImpl;
import hello.basic.member.MemoryMemberRepository;
import hello.basic.order.OrderService;
import hello.basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService");// 1
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public MemberService memberService() {
    System.out.println("call AppConfig.memberService"); // 4
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public MemoryMemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository"); // 2
    return new MemoryMemberRepository();
  }


  @Bean
  public DiscountPolicy discountPolicy() {
    System.out.println("call AppConfig.discountPolicy"); // 3
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }
}
