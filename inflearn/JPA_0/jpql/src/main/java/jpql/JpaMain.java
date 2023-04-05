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

//      String query = "select " +
//          "case when m.age<=10 then '학생요금' " +
//          "when m.age<=60 then '경로요금 '" +
//          "else '일반요금 '" +
//          "end "+
//          "from Member m";
//      String query = "select coalesce(m.username,'이름 없는 회원') from Member m"; //default 설정
      String query = "select nullif(m.username,'member1') from Member m"; // member1이면 null반환

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
