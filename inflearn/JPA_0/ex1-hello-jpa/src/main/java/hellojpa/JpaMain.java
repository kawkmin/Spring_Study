package hellojpa;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Hibernate;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team team = new Team();
      team.setName("teamA");
      em.persist(team);

      Member member1 = new Member();
      member1.setName("member1");
      member1.setTeam(team);
      em.persist(member1);

      em.flush();
      em.clear();

//      Member m = em.getReference(Member.class, member1.getId());
//
//      System.out.println("m = " + m.getTeam().getClass());
//
//      System.out.println("=========");
//      m.getTeam().getClass(); //초기화 .실제 사용할 때 쿼리 실행
//      System.out.println("=========");

      List<Member> members = em.createQuery("select m from Member m", Member.class)
          .getResultList();
      //sql: select * from Member 쿼리 실행 후, 보니까 즉시지연이라 Team도 바로 가져오는 쿼리 실행 -> 각 맴버마다 팀 다 가져옴(1+N개)
      // lazy로 모두 가져오는 쿼리 하고싶으면, select m from Member m fetch join 쓰면 되니까 모두 lazy로 일단 설정하자

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
