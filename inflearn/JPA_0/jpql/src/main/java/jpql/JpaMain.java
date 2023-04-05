package jpql;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      Team teamA = new Team();
      teamA.setName("팀A");
      em.persist(teamA);
      Team teamB = new Team();
      teamB.setName("팀B");
      em.persist(teamB);

      Member member1 = new Member();
      member1.setUsername("회원1");
      member1.setTeam(teamA);
      em.persist(member1);
      Member member2 = new Member();
      member2.setUsername("회원2");
      member2.setTeam(teamA);
      em.persist(member2);
      Member member3 = new Member();
      member3.setUsername("회원3");
      member3.setTeam(teamB);
      em.persist(member3);

      em.flush();
      em.clear();

//      String query = "select m From Member m";
//
//      List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//      for (Member member : result) {
//        System.out.println("member = " + member.getUsername());
//        System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
//        //회원1 sql
//        //회원2 1차캐시
//        //회원3 sql
//
//        //회원 100명 -> N + 1문제 발생
//      }
      //패치
//      String query = "select m From Member m join fetch m.team";
//      List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//      for (Member member : result) {
//        System.out.println("member = " + member.getUsername());
//        System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
//      }
      //컬렉션 패치 -> 팀A가 2번나옴
//      String query = "select t From Team t join fetch t.members";
      String query = "select distinct t From Team t join fetch t.members"; //중복 제거 (같은 식별자 엔티티 제거)
      List<Team> result = em.createQuery(query, Team.class).getResultList();

      for (Team team : result) {
        System.out.println("team.getName() = " + team.getName());
        for (Member member : team.getMembers()) {
          System.out.println("member = " + member);
        }
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }

    emf.close();
  }
}
