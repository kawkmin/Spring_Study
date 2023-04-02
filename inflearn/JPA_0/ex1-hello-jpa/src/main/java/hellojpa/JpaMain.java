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

/*      IDENTITY
      System.out.println("===========");
      em.persist(member); //flush를 안해도, 쿼리실행.(IDENTITY는 PK를 모르기 때문)
      System.out.println("member.getId() = " + member.getId());
      System.out.println("===========");*/

/*      SEQUENCE
      System.out.println("===========");
      em.persist(member); //쿼리 발생X, 스퀀스에서 가져옴.
      System.out.println("member.getId() = " + member.getId());
      System.out.println("===========");*/


      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
