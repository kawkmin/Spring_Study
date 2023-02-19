package hello.basic.autowired;

import static org.assertj.core.api.Assertions.*;

import hello.basic.AutoAppConfig;
import hello.basic.discount.DiscountPolicy;
import hello.basic.member.Grade;
import hello.basic.member.Member;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AllBeanTest {

  @Test
  void findAllBean() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,
        DiscountService.class);

    DiscountService discountService = ac.getBean(DiscountService.class);
    Member member = new Member(1L, "userA", Grade.VIP);
    int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

    assertThat(discountService).isInstanceOf(DiscountService.class);
    assertThat(discountPrice).isEqualTo(1000);

    int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
    assertThat(rateDiscountPrice).isEqualTo(2000);
  }

  static class DiscountService {

    private final Map<String, DiscountPolicy> policyMap; // 여러개의 빈 모두 저장 (rate,fix 등)
    private final List<DiscountPolicy> policies;

    // @Autowired 하나라서 생략 가능
    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }

    public int discount(Member member, int price, String discountCode) {
      DiscountPolicy discountPolicy = policyMap.get(discountCode);
      return discountPolicy.discount(member, price);
    }
  }
}
