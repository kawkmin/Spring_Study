package hello.basic.order;

import hello.basic.discount.DiscountPolicy;
import hello.basic.member.Member;
import hello.basic.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 생성자 자동
public class OrderServiceImpl implements OrderService {

  // @Autowired 필드 주입 (테스트가 힘들어 비권장)
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  /* @Atuowired setter 주입
  public void setMemberRepository(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  } */

  // @Autowired 생성자가 한개면, 생략 가능
/*  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }*/

  /* 일반 메서드 주입(잘 사용하지 않음)
  @Autowired
  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }*/

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findByID(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
