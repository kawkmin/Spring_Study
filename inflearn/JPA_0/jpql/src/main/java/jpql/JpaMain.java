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
      member1.setAge(0);
      member1.setTeam(teamA);
      em.persist(member1);

      Member member2 = new Member();
      member2.setUsername("회원2");
      member2.setAge(0);
      member2.setTeam(teamA);
      em.persist(member2);

      Member member3 = new Member();
      member3.setUsername("회원3");
      member3.setAge(0);
      member3.setTeam(teamB);
      em.persist(member3);

      int resultCount = em.createQuery("update Member m set m.age= 20")
          .executeUpdate();
      System.out.println("resultCount = " + resultCount);

      em.clear(); //없으면 member1 나이를 0으로 가져옴
      Member findMember1 = em.find(Member.class, member1.getId());

      System.out.println("member1 = " + findMember1.getAge());

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
