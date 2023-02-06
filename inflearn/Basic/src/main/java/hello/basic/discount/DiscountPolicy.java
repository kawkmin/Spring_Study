package hello.basic.discount;

import hello.basic.member.Member;

public interface DiscountPolicy {

  int discount(Member member, int price);
}
