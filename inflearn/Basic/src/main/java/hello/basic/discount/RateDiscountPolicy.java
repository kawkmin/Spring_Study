package hello.basic.discount;

import hello.basic.member.Grade;
import hello.basic.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy") // 여러개의 빈일 때, 이름으로 매칭하게 (@Primary 보다 우선순위 높음)
@Primary // 여러개의 빈일 때, 우선으로
public class RateDiscountPolicy implements DiscountPolicy {

  private int discountPercent = 10;


  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    } else {
      return 0;
    }
  }
}
