package hellojpa;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Hibernate;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Member member1 = new Member();
      member1.setName("member1");

      em.persist(member1);

      em.flush();
      em.clear();

//      Member m1 = em.find(Member.class, member1.getId()); //쿼리 나감
//      System.out.println("m1.getClass() = " + m1.getClass());
//      Member reference = em.getReference(Member.class, member1.getId()); // 쿼리 안나감
//      System.out.println("reference.getClass() = " + reference.getClass());
//
//      System.out.println("a == a: " + (m1 == reference)); // 굳이 1차캐시에 엔티티가 있으면, 프록시 안함, jpa는 무조건 참 보장

      //System.out.println("findMember.getName() = " + m1.getName()); //쿼리 다시 나감
// =====================================================================================
      Member refMember = em.getReference(Member.class, member1.getId());
      System.out.println("refMember.getClass() = " + refMember.getClass());
// =====================================================================================
//      Member findMember = em.find(Member.class, member1.getId()); //find 이지만 밑에 ==을 지키기 위해, prox로..
//      System.out.println("findMember.getClass() = " + findMember.getClass());
//
//      System.out.println("refMember == findMember: " + (refMember == findMember));
// =====================================================================================
//      em.detach(refMember);
//      //em.close();
//      //em.clear();
//
//      refMember.getName(); // 불가능
// =====================================================================================
      //refMember.getName(); 강제 초기화
      Hibernate.initialize(refMember); //강제 초기화
      System.out.println(
          "isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //초기화 여부 확인

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
