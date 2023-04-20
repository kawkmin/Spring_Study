package study.querydsl;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
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
}
