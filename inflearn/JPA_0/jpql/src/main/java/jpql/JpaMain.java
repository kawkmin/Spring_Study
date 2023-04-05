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

//      String query = "select m.team.name from Member m";
//
//      List<Team> result = em.createQuery(query, Team.class).getResultList(); // 묵시적 내부 조인 (비추)

//      String query = "select t.members from Team t"; // t.members.username 불가능
      String query = "select m.username From Team t join t.members m"; // 명시적 조인
      List<Collection> result = em.createQuery(query, Collection.class).getResultList();
      System.out.println("result = " + result);

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
