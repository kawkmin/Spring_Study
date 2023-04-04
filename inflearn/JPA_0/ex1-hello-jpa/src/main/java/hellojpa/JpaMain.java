package hellojpa;

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
      Address address = new Address("city", "street", "1000");

      Member member = new Member();
      member.setName("member1");
      member.setHomeAddress(address);
      em.persist(member);

////      Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//      Member member2 = new Member();
//      member2.setName("member2");
//      member2.setHomeAddress(address);
//      em.persist(member2);
//
////      member.getHomeAddress().setCity("newCity"); // 둘 다 바뀜

      Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
      member.setHomeAddress(newAddress); // 항상 새로 만들어서 변경

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
