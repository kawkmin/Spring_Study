package jpql;

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
      Team team = new Team();
      team.setName("teamA");
      em.persist(team);

      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      member.changeTeam(team);

      em.persist(member);

      em.flush();
      em.clear();

      String query = "select m from Member m left join m.team t on t.name = 'teamA'";
      List<Member> resultList = em.createQuery(query, Member.class)
          .getResultList();

      System.out.println("resultList.size() = " + resultList.size());
      for (Member member1 : resultList) {
        System.out.println("member1 = " + member1);
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
