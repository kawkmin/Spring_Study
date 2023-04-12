package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository; //인터페이스를 인스턴스 생성? -> 프록시
  @Autowired
  TeamRepository teamRepository;
  @PersistenceContext
  EntityManager em;

//  @Autowired
//  MemberQueryRepository memberQueryRepository;

  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(savedMember.getId()).get();

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    memberRepository.save(member1);
    memberRepository.save(member2);

    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    List<Member> all = memberRepository.findAll();
    assertThat(all.size()).isEqualTo(2);

    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    memberRepository.delete(member1);
    memberRepository.delete(member2);

    long count1 = memberRepository.count();
    assertThat(count1).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThen() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("AAA", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

    assertThat(result.get(0).getUsername()).isEqualTo("AAA");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void findHelloBy() {
    List<Member> helloBy = memberRepository.findTop3HelloBy();
  }

  @Test
  public void testNamedQuery() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByUsername("AAA");
    assertThat(result.get(0)).isEqualTo(m1);
  }

  @Test
  public void testQuery() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findUser("AAA", 10);
    assertThat(result.get(0)).isEqualTo(m1);
  }

  @Test
  public void findUsernameList() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<String> result = memberRepository.findUsernameList();
    for (String s : result) {
      System.out.println("s = " + s);
    }
  }

  @Test
  public void findMemberDto() {
    Team team = new Team("teamA");
    teamRepository.save(team);

    Member m1 = new Member("AAA", 10);
    m1.setTeam(team);
    memberRepository.save(m1);

    List<MemberDto> result = memberRepository.findMemberDto();
    for (MemberDto memberDto : result) {
      System.out.println("memberDto = " + memberDto);
    }
  }

  @Test
  public void findByNames() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> names = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
    for (Member name : names) {
      System.out.println("name = " + name);
    }
  }

  @Test
  public void returnType() {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    Optional<Member> findMember = memberRepository.findOptionalByUsername("asd");
    System.out.println("findMember = " + findMember);
  }

  @Test
  public void paging() {
    memberRepository.save(new Member(("member1"), 10));
    memberRepository.save(new Member(("member2"), 10));
    memberRepository.save(new Member(("member3"), 10));
    memberRepository.save(new Member(("member4"), 10));
    memberRepository.save(new Member(("member5"), 10));

    int age = 10;
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

    Page<Member> page = memberRepository.findByAge(age, pageRequest);
    //Slice<Member> page = memberRepository.findByAge(age, pageRequest); //count query 없음

    Page<MemberDto> toMap = page.map(
        member -> new MemberDto(member.getId(), member.getUsername(), null)); // api

    List<Member> content = page.getContent();

    assertThat(content.size()).isEqualTo(3);
    assertThat(page.getTotalElements()).isEqualTo(5); //
    assertThat(page.getNumber()).isEqualTo(0);
    assertThat(page.getTotalPages()).isEqualTo(2); //
    assertThat(page.isFirst()).isTrue();
    assertThat(page.hasNext()).isTrue();
  }

  @Test
  public void bulkUpdate() {
    memberRepository.save(new Member(("member1"), 10));
    memberRepository.save(new Member(("member2"), 19));
    memberRepository.save(new Member(("member3"), 20));
    memberRepository.save(new Member(("member4"), 21));
    memberRepository.save(new Member(("member5"), 40));

    int resultCount = memberRepository.bulkAgePlus(20);
    //em.clear();

    List<Member> result = memberRepository.findByUsername("member5");
    Member member = result.get(0);
    System.out.println("member.getAge() = " + member.getAge()); //em.clear 안하면 41이 안나오고 40이 나옴

    assertThat(resultCount).isEqualTo(3);
  }

  @Test
  public void findMemberLazy() {
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 10, teamB);
    memberRepository.save(member1);
    memberRepository.save(member2);

    em.flush();
    em.clear();

    //List<Member> members = memberRepository.findAll(); // @EntityGraph 전 N+1
    //List<Member> members = memberRepository.findMemberFetchJoin();
    List<Member> members = memberRepository.findAll();
    for (Member member : members) {
      System.out.println("member.getUsername() = " + member.getUsername());
      System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass()); // 프록시
      System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
    }
  }

  @Test
  public void queryHint() {
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();

    Member findMember = memberRepository.findReadOnlyByUsername("member1");
    findMember.setUsername("member2");

    em.flush(); //Update Query 실행X
  }

  @Test
  public void lock() {
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();

    List<Member> result = memberRepository.findLocksByUsername("member1");
  }

  @Test
  public void callCustom() {
    List<Member> result = memberRepository.findMemberCustom(); //인터페이스를 구현한 것을 찾아서 알아서 써줌
  }

  @Test
  public void specBasic() throws Exception {
    //given
    Team teamA = new Team("teamA");
    em.persist(teamA);
    Member m1 = new Member("m1", 0, teamA);
    Member m2 = new Member("m2", 0, teamA);
    em.persist(m1);
    em.persist(m2);
    em.flush();
    em.clear();
    //when
    Specification<Member> spec =
        MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
    List<Member> result = memberRepository.findAll(spec);
    //then
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void projections() {

    Team teamA = new Team("teamA");
    em.persist(teamA);

    em.persist(new Member("m1", 0, teamA));
    em.persist(new Member("m2", 0, teamA));
    em.flush();
    em.clear();

//    List<UsernameOnly> result = memberRepository.findProjectionsByUsername("m1");
//
//    for (UsernameOnly usernameOnly : result) {
//      System.out.println("usernameOnly = " + usernameOnly.getUsername());
//    }
//    List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("m1",
//        UsernameOnlyDto.class);
//
//    for (UsernameOnlyDto UsernameOnlyDto : result) {
//      System.out.println("usernameOnly = " + UsernameOnlyDto.getUsername());
    List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("m1",
        NestedClosedProjections.class);

    for (NestedClosedProjections nestedClosedProjections : result) {
      System.out.println("usernameOnly = " + nestedClosedProjections.getUsername());
      System.out.println(
          "nestedClosedProjections.getTeam().getName() = " + nestedClosedProjections.getTeam()
              .getName());
    }
  }

  @Test
  public void nativeQuery() {
    Team teamA = new Team("teamA");
    em.persist(teamA);

    em.persist(new Member("m1", 0, teamA));
    em.persist(new Member("m2", 0, teamA));
    em.flush();
    em.clear();

    Member result = memberRepository.findByNativeQuery("m1");
    System.out.println("result = " + result);
  }

  @Test
  public void nativeProjectionsQuery() {
    Team teamA = new Team("teamA");
    em.persist(teamA);

    em.persist(new Member("m1", 0, teamA));
    em.persist(new Member("m2", 0, teamA));
    em.flush();
    em.clear();
    Page<MemberProjections> result = memberRepository.findByNativeProjection(
        PageRequest.of(0, 10));
    System.out.println("result = " + result);
  }
}