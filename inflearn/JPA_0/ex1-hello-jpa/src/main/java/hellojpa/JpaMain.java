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

      Member member = em.find(Member.class, 150L);
      member.setName("AAA");

      //준영속
      em.detach(member);
      //em.clear();
      //em.close(); 종료

      Member member2 = em.find(Member.class, 150L);
      System.out.println("member2 = " + member2.getName()); // ZZZ

      System.out.println("===========");
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
