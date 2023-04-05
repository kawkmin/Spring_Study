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
      member.setType(MemberType.ADMIN);
      member.changeTeam(team);

      em.persist(member);

      em.flush();
      em.clear();

      String query = "select 'a' || 'b' from Member m"; //concat('a','b')
      //substring(),locate('de','abcdef'),size(t.members),index()..등등 많은 기능
      //select group_concat(i.name) from Item i <- 커스텀 함수 사용 가능

      List<String> resultList = em.createQuery(query, String.class).getResultList();

      for (String s : resultList) {
        System.out.println("s = " + s);
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
