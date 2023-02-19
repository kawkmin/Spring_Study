package hello.basic.order;

import static org.junit.jupiter.api.Assertions.*;

import hello.basic.discount.FixDiscountPolicy;
import hello.basic.member.Grade;
import hello.basic.member.Member;
import hello.basic.member.MemberRepository;
import hello.basic.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

  @Test
  void createOrder() {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    memberRepository.save(new Member(1L, "name", Grade.VIP));

    OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(),
        new FixDiscountPolicy());

    Order order = orderService.createOrder(1L, "itemA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
  }

}