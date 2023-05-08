package study.querydsl.repository;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public MemberRepositoryImpl(EntityManager em) {
    queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<MemberTeamDto> search(MemberSearchCondition condition) {
    return queryFactory
        .select(new QMemberTeamDto(
            member.id.as("memberId"),
            member.username,
            member.age,
            team.id.as("teamId"),
            team.name.as("teamName")))
        .from(member)
        .leftJoin(member.team, team)
        .where(
            usernameEq(condition.getUserName()),
            teamNameEq(condition.getTeamName()),
            ageGoe(condition.getAgeGoe()),
            ageLoe(condition.getAgeLoe())
        )
        .fetch();
  }

  @Override
  public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
    QueryResults<MemberTeamDto> results = queryFactory
        .select(new QMemberTeamDto(
            member.id.as("memberId"),
            member.username,
            member.age,
            team.id.as("teamId"),
            team.name.as("teamName")))
        .from(member)
        .leftJoin(member.team, team)
        .where(
            usernameEq(condition.getUserName()),
            teamNameEq(condition.getTeamName()),
            ageGoe(condition.getAgeGoe()),
            ageLoe(condition.getAgeLoe())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    List<MemberTeamDto> content = results.getResults();
    long total = results.getTotal();

    return new PageImpl<>(content, pageable, total);
  }

  @Override
  public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
    List<MemberTeamDto> content = queryFactory
        .select(new QMemberTeamDto(
            member.id.as("memberId"),
            member.username,
            member.age,
            team.id.as("teamId"),
            team.name.as("teamName")))
        .from(member)
        .leftJoin(member.team, team)
        .where(
            usernameEq(condition.getUserName()),
            teamNameEq(condition.getTeamName()),
            ageGoe(condition.getAgeGoe()),
            ageLoe(condition.getAgeLoe())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

//    5.0 이전 버전은 .fetchCount()가 있다
//    long total = queryFactory
//        .selectFrom(member)
//        .leftJoin(member.team, team)
//        .where(
//            usernameEq(condition.getUserName()),
//            teamNameEq(condition.getTeamName()),
//            ageGoe(condition.getAgeGoe()),
//            ageLoe(condition.getAgeLoe())
//        )
//        .fetchCount();

    // 5.0 이후 버전은 Entity.count()가 있다
    Long total = queryFactory
        .select(member.count())
        .from(member)
        .leftJoin(member.team, team)
        .where(
            usernameEq(condition.getUserName()),
            teamNameEq(condition.getTeamName()),
            ageGoe(condition.getAgeGoe()),
            ageLoe(condition.getAgeLoe())
        )
        .fetchOne();

    return new PageImpl<>(content, pageable, total);
  }

  private BooleanExpression usernameEq(String userName) {
    return hasText(userName) ? member.username.eq(userName) : null;
  }

  private BooleanExpression teamNameEq(String teamName) {
    return hasText(teamName) ? team.name.eq(teamName) : null;
  }

  private BooleanExpression ageGoe(Integer ageGoe) {
    return ageGoe != null ? member.age.goe(ageGoe) : null;
  }

  private BooleanExpression ageLoe(Integer ageLoe) {
    return ageLoe != null ? member.age.loe(ageLoe) : null;
  }

}
