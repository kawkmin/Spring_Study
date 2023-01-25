package com.example.start.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.start.domain.Member;
import com.example.start.repository.MemberRepository;
import com.example.start.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

  MemberService memberService;
  MemoryMemberRepository memberRepository;

  @BeforeEach
  public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
  }

  @AfterEach()
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  void join() {
    Member member = new Member();
    member.setName("spring");

    Long saveId = memberService.join(member);

    Member findMember = memberService.findOne(saveId).get();
    assertThat(member.getName()).isEqualTo(findMember.getName());
  }

  @Test
  public void 중복_회원_예외() {
    Member member1 = new Member();
    member1.setName("spring");

    Member member2 = new Member();
    member2.setName("spring");

    memberService.join(member1);
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> memberService.join(member2));

    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

/*    try {
      memberService.join(member2);
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }*/
  }

  @Test
  void findMembers() {
  }

  @Test
  void findOne() {
  }
}