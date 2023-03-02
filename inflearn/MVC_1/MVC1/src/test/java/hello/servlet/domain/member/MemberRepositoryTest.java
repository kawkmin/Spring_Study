package hello.servlet.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

  MemberRepository memberRepository = MemberRepository.getInstance();

  @AfterEach
  void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  void save() {
    Member member = new Member("hello", 20);

    Member saveMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(member.getId());
    assertThat(findMember).isEqualTo(saveMember);
  }

  @Test
  void findAll() {
    Member member1 = new Member("member1", 20);
    Member member2 = new Member("member2", 30);

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> result = memberRepository.findAll();

    assertThat(result.size()).isEqualTo(2);
    assertThat(result).contains(member1, member2);
  }
}