package study.querydsl;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

  @Autowired
  EntityManager em;

  JPAQueryFactory queryFactory;

  @BeforeEach
  public void before() {
    queryFactory = new JPAQueryFactory(em);
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    em.persist(teamA);
    em.persist(teamB);
    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 20, teamA);
    Member member3 = new Member("member3", 30, teamB);
    Member member4 = new Member("member4", 40, teamB);
    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);
  }

  @Test
  public void startJPQL() {
    Member findMember = em.createQuery("select m from Member m where m.username =:username",
            Member.class)
        .setParameter("username", "member1")
        .getSingleResult();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void startQuerydsl() {
    //QMember m = new QMember("m");
    //QMember m = QMember.member;

    Member findMember = queryFactory
        .select(member) //QMember.member 에서 static import (권장)
        .from(member)
        .where(member.username.eq("member1"))
        .fetchOne();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void search() {
    Member findMember = queryFactory
        .selectFrom(member)
        .where(member.username.eq("member1").
            and(member.age.eq(10)))
        .fetchOne();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void searchAndParam() {
    Member findMember = queryFactory
        .selectFrom(member)
        .where(//...
            member.username.eq("member1"),
            member.age.eq(10) // 추후 동적 쿼리에 유리함
        )
        .fetchOne();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void resultFetch() {
    List<Member> fetch = queryFactory
        .select(member)
        .fetch();

    Member fetchOne = queryFactory
        .select(member)
        .fetchOne();

    Member fetchFirst = queryFactory
        .select(member)
        .fetchFirst();

    QueryResults<Member> results = queryFactory // 5.0부터 지원 X
        .selectFrom(member)
        .fetchResults();

    results.getTotal(); //쿼리 나감
    List<Member> content = results.getResults();

    long total = queryFactory // 5.0부터 지원 X
        .selectFrom(member)
        .fetchCount();

  }

  @Test
  public void sort() {
    em.persist(new Member(null, 100));
    em.persist(new Member("member5", 100));
    em.persist(new Member("member6", 100));

    List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.eq(100))
        .orderBy(member.age.desc(), member.username.asc().nullsLast()) //널을 마지막으로
        .fetch();

    Member member5 = result.get(0);
    Member member6 = result.get(1);
    Member memberNull = result.get(2);
    assertThat(member5.getUsername()).isEqualTo("member5");
    assertThat(member6.getUsername()).isEqualTo("member6");
    assertThat(memberNull.getUsername()).isNull();
  }

  @Test
  public void paging1() {
    List<Member> result = queryFactory
        .selectFrom(member)
        .orderBy(member.username.desc())
        .offset(1)
        .limit(2)
        .fetch();

    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  public void paging2() {
    QueryResults<Member> queryResults = queryFactory
        .selectFrom(member)
        .orderBy(member.username.desc())
        .offset(1)
        .limit(2)
        .fetchResults();

    assertThat(queryResults.getTotal()).isEqualTo(4);
    assertThat(queryResults.getLimit()).isEqualTo(2);
    assertThat(queryResults.getOffset()).isEqualTo(1);
    assertThat(queryResults.getResults().size()).isEqualTo(2);
  }

  @Test
  public void aggregation() {
    List<Tuple> result = queryFactory
        .select(
            member.count(),
            member.age.sum(),
            member.age.avg(),
            member.age.max(),
            member.age.min()
        )
        .from(member)
        .fetch();

    Tuple tuple = result.get(0);
    assertThat(tuple.get(member.count())).isEqualTo(4);
    assertThat(tuple.get(member.age.sum())).isEqualTo(100);
    assertThat(tuple.get(member.age.avg())).isEqualTo(25);
    assertThat(tuple.get(member.age.max())).isEqualTo(40);
    assertThat(tuple.get(member.age.min())).isEqualTo(10);
  }

  @Test
  public void group() {
    List<Tuple> result = queryFactory
        .select(team.name, member.age.avg())
        .from(member)
        .join(member.team, team)
        .groupBy(team.name)
        .fetch();
    Tuple teamA = result.get(0);
    Tuple teamB = result.get(1);

    assertThat(teamA.get(team.name)).isEqualTo("teamA");
    assertThat(teamA.get(member.age.avg())).isEqualTo(15);

    assertThat(teamB.get(team.name)).isEqualTo("teamB");
    assertThat(teamB.get(member.age.avg())).isEqualTo(35);
  }

  @Test
  public void join() {
    List<Member> result = queryFactory
        .selectFrom(member)
        .join(member.team, team)
        .where(team.name.eq("teamA"))
        .fetch();

    assertThat(result)
        .extracting("username")
        .containsExactly("member1", "member2");
  }

  // 연관관계 없는 테이블 필드로 조인 (세타 조인)
  @Test
  public void theta_join() {
    em.persist(new Member("teamA"));
    em.persist(new Member("teamB"));

    List<Member> result = queryFactory
        .select(member)
        .from(member, team) // 모든 팀도 가져옴
        .where(member.username.eq(team.name))
        .fetch();

    assertThat(result)
        .extracting("username")
        .containsExactly("teamA", "teamB");
  }

  @Test
  public void join_on_filtering() {
    List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        //.join(member.team, team).on(team.name.eq("teamA")) //leftJoin...등 같은 경우
        .join(member.team, team)
        .where(team.name.eq("teamA")) // 익숙한 where절 선호 (innerJoin이면 on이랑 같음)
        .fetch();

    for (Tuple tuple : result) {
      System.out.println("tuple = " + tuple);
      /*
      leftJoin
      tuple = [Member(id=1, username=member1, age=10), Team(id=1, name=teamA)]
      tuple = [Member(id=2, username=member2, age=20), Team(id=1, name=teamA)]
      tuple = [Member(id=3, username=member3, age=30), null] // teamB라서 조인 X
      tuple = [Member(id=4, username=member4, age=40), null]

      join(inner) or where()
      tuple = [Member(id=1, username=member1, age=10), Team(id=1, name=teamA)]
      tuple = [Member(id=2, username=member2, age=20), Team(id=1, name=teamA)]
       */

    }
  }

  // 연관관계 없는 엔티티 외부 조인
  @Test
  public void join_on_no_relation() {
    em.persist(new Member("teamA"));
    em.persist(new Member("teamB"));
    em.persist(new Member("teamC"));
    em.flush();
    em.clear();

    List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        .leftJoin(team).on(member.username.eq(team.name)) //묵시적 조인 발생 X
        .fetch();

    for (Tuple tuple : result) {
      System.out.println("tuple = " + tuple);
      /*
      tuple = [Member(id=1, username=member1, age=10), null]
      tuple = [Member(id=2, username=member2, age=20), null]
      tuple = [Member(id=3, username=member3, age=30), null]
      tuple = [Member(id=4, username=member4, age=40), null]
      tuple = [Member(id=5, username=teamA, age=0), Team(id=1, name=teamA)] //조건 만족만 가져옴
      tuple = [Member(id=6, username=teamB, age=0), Team(id=2, name=teamB)]
      tuple = [Member(id=7, username=teamC, age=0), null]
       */
    }
  }

  @PersistenceUnit
  EntityManagerFactory emf;

  @Test
  public void fetchJoinNo() {
    em.flush();
    em.clear();

    Member findMember = queryFactory
        .selectFrom(member)
        .join(member.team, team).fetchJoin()
        .where(member.username.eq("member1"))
        .fetchOne();

    boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
    assertThat(loaded).as("패치 조인 적용").isTrue();

  }

  @Test
  public void subQuery() {

    QMember memberSub = new QMember("memberSub");

    List<Member> result = queryFactory
        .select(member)
        .where(member.age.eq(
            JPAExpressions //staticImport 가능
                .select(memberSub.age.max())
                .from(memberSub)
        ))
        .fetch();

    assertThat(result).extracting("age").containsExactly(40);
  }

  @Test
  public void subQueryGoe() {

    QMember memberSub = new QMember("memberSub");

    List<Member> result = queryFactory
        .select(member)
        .where(member.age.goe(
            JPAExpressions
                .select(memberSub.age.avg())
                .from(memberSub)
        ))
        .fetch();

    assertThat(result).extracting("age").containsExactly(30, 40);
  }

  @Test
  public void subQueryIn() {

    QMember memberSub = new QMember("memberSub");

    List<Member> result = queryFactory
        .select(member)
        .where(member.age.in(
            JPAExpressions
                .select(memberSub.age)
                .from(memberSub)
                .where(memberSub.age.gt(10))
        ))
        .fetch();

    assertThat(result).extracting("age").containsExactly(20, 30, 40);
  }

  @Test
  public void selectSubquery() {

    QMember memberSub = new QMember("memberSub");

    List<Tuple> result = queryFactory
        .select(member.username,
            JPAExpressions
                .select(memberSub.age.avg())
                .from(memberSub))
        .from(member)
        .fetch();

    for (Tuple tuple : result) {
      System.out.println("tuple = " + tuple);
    }
  }

  @Test
  public void basicCase() {
    List<String> result = queryFactory
        .select(member.age
            .when(10).then("열살")
            .when(20).then("스물살")
            .otherwise("기타"))
        .from(member)
        .fetch();

    for (String s : result) {
      System.out.println("s = " + s);
    }
  }

  @Test
  public void complexCase() {
    List<String> result = queryFactory
        .select(new CaseBuilder()
            .when(member.age.between(0, 20)).then("0~20살")
            .when(member.age.between(21, 30)).then("21~30살")
            .otherwise("기타"))
        .from(member)
        .fetch();

    for (String s : result) {
      System.out.println("s = " + s);
    }
  }

  @Test
  public void constant() {
    List<Tuple> result = queryFactory
        .select(member.username, Expressions.constant("A")) // 상수
        .from(member)
        .fetch();

    for (Tuple tuple : result) {
      System.out.println("tuple = " + tuple); //[?,A] ...[?,A]
    }
  }

  @Test
  public void concat() {
    List<String> result = queryFactory
        .select(member.username.concat("_").concat(member.age.stringValue()))
        .from(member)
        .where(member.username.eq("member1"))
        .fetch();

    for (String s : result) {
      System.out.println("s = " + s);
    }
  }

  @Test
  public void simpleProjection() {
    List<String> fetch = queryFactory
        .select(member.username)
        .from(member)
        .fetch();
    for (String s : fetch) {
      System.out.println("s = " + s);
    }
  }

  @Test
  public void tupleProjection() {
    List<Tuple> fetch = queryFactory
        .select(member.username, member.age)
        .from(member)
        .fetch();

    for (Tuple tuple : fetch) {
      String username = tuple.get(member.username);
      Integer integer = tuple.get(member.age);
      System.out.println("username = " + username);
      System.out.println("integer = " + integer);
    }
  }

  @Test
  public void findDtoByJPQL() {
    List<MemberDto> result = em.createQuery(
            "select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m",
            MemberDto.class)
        .getResultList();

    for (MemberDto memberDto : result) {
      System.out.println("memberDto = " + memberDto);
    }
  }

  @Test
  public void findDtoBySetterConstructorFiled() {
    List<MemberDto> result = queryFactory
//        .select(Projections.bean(MemberDto.class, member.username, member.age)) // setter로 (record는 안됨)
        .select(Projections.constructor(MemberDto.class, member.username, member.age)) // 생성자로
        // .select(Projections.fields(MemberDto.class, member.username, member.age)) // 필드로 (record는 안됨)
        .from(member)
        .fetch();

    for (MemberDto memberDto : result) {
      System.out.println("memberDto = " + memberDto);
    }
  }

  @Test
  public void findUserDto() {
    QMember memberSub = new QMember("memberSub");
    List<UserDto> result = queryFactory
        .select(
            Projections.fields(UserDto.class, member.username.as("name"),

                ExpressionUtils.as(JPAExpressions
                    .select(memberSub.age.max())
                    .from(memberSub), "age")
            ))
        .from(member)
        .fetch();

    for (UserDto userDto : result) {
      System.out.println("userDto = " + userDto);
    }
  }

  @Test
  public void findUserDtoByConstructor() {
    List<UserDto> result = queryFactory
        .select(Projections.constructor(UserDto.class, member.username, member.age))
        .from(member)
        .fetch();

    for (UserDto userDto : result) {
      System.out.println("userDto = " + userDto);
    }
  }

}
