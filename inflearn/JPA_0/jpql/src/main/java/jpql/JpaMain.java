package jpql;

import java.util.List;
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

      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      em.persist(member);

//      TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//
//      List<Member> resultList = query1.getResultList(); //여러개 일 때 (결과 없으면 빈 리스트)
//
//      Member re = query1.getSingleResult(); //한 개 일때 (결과가 한개가 아니면, 오류)
//
//      TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//      Query query3 = em.createQuery("select m.username,m.age from Member m");

      TypedQuery<Member> query = em.createQuery(
          "select m from Member m where  m.username=:username",
          Member.class); //:1 (1,"member1) <-위치 기준(비추천)
      query.setParameter("username", "member1");
      Member singleResult = query.getSingleResult();
      System.out.println(singleResult);

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
