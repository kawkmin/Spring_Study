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

      String query = "select m.username, 'HELLO',true from Member m where m.type=:userType"; //파라미터 아니면 패키지명까지 써야함
      List<Object[]> result = em.createQuery(query)
          .setParameter("userType", MemberType.ADMIN)
          .getResultList();

      for (Object[] objects : result) {
        System.out.println("objects[0] = " + objects[0]);
        System.out.println("objects[0] = " + objects[1]);
        System.out.println("objects[0] = " + objects[2]);
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
