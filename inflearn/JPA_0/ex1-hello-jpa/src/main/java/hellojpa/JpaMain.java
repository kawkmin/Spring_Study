package hellojpa;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

//      em.createQuery(
//              "select m FROM Member m where m.name like '%kim%'", Member.class)
//          .getResultList();

//      //Criteria 사용 준비 (비추)
//      CriteriaBuilder cb = em.getCriteriaBuilder();
//      CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//      Root<Member> m = query.from(Member.class);
//
//      CriteriaQuery<Member> cq = query.select(m);
//      String Flag = "asdf";
//      if (Flag != null) { //동적 쿼리
//        cq.where(cb.equal(m.get("username"), "kim"));
//      }
//      List<Member> resultList = em.createQuery(cq)
//          .getResultList();

      Member member = new Member();
      member.setName("member1");
      em.persist(member);

      //flush -> commit , query(쿼리에선 DB로 찾아오고, 영속성 캐쉬를 안받기 때문에(원랜 flush 하기전엔 캐쉬로 획득함), 방지하고자 flush 강제로됨)

      // 네이티브 쿼리
      List resultList = em.createNativeQuery(
              "select MEMBER_ID, city, street,zipcode,USERNAME FROM MEMBER;")
          .getResultList();

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
