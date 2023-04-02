package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team team = new Team();
      team.setName("TeamA");
      em.persist(team);

      Member member = new Member();
      member.setName("member1");
      member.setTeam(team);
      em.persist(member);

      team.getMembers().add(member); //역방향에 안넣어주면, flush하지 않는 이상, mappedBy안됨

      //em.flush();
      //em.clear();

      Member findMember = em.find(Member.class, member.getId());
      List<Member> members = findMember.getTeam().getMembers();

      for (Member member1 : members) {
        System.out.println("member1 = " + member1.getName());
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
