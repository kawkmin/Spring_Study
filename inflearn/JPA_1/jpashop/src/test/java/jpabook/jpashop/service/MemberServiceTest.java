package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em; //db tarce 보기위함

  @Test
  public void 회원가입() {
    Member member = new Member();
    member.setName("kim");

    Long savedId = memberService.join(member);

    em.flush();
    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test
  public void 중복_회원_예외() {

    Member member1 = new Member();
    member1.setName("kim");

    Member member2 = new Member();
    member2.setName("kim");

    memberService.join(member1);
    Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(
        IllegalStateException.class);
  }

}