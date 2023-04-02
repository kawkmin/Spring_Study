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

      //회원 저장
      Member member = new Member();
      member.setName("member1");
      em.persist(member);

      // 저장
      Team team = new Team();
      team.setName("TeamA");
      team.getMembers().add(member); //오류 주인에 값을 설정해야함.
      em.persist(team);

      em.flush();
      em.clear();

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
