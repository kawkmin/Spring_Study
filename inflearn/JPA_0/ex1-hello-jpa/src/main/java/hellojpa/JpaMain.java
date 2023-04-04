package hellojpa;

import java.util.List;
import java.util.Set;
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

      Member member = new Member();
      member.setName("member1");
      member.setHomeAddress(new Address("city1", "street", "10000"));

      member.getFavoriteFoods().add("치킨");
      member.getFavoriteFoods().add("족발");
      member.getFavoriteFoods().add("피자");

      member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
      member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

      em.persist(member); //라이프 사이클이 같다

      em.flush();
      em.clear();

      System.out.println("===========================");
      Member findMember = em.find(Member.class, member.getId());

//      List<Address> addressHistory = findMember.getAddressHistory(); //지연 로딩 확인
//      for (Address address : addressHistory) {
//        System.out.println("address = " + address);
//      }
//
//      Set<String> favoriteFoods = findMember.getFavoriteFoods();
//      for (String favoriteFood : favoriteFoods) {
//        System.out.println("favoriteFood = " + favoriteFood);
//      }

      // set 지양
//      findMember.getHomeAddress().setCity("newCity");
//      findMember.setHomeAddress(new Address("newCity", findMember.getHomeAddress().getStreet(),
//          findMember.getHomeAddress().getStreet()));
//
//      findMember.getFavoriteFoods().remove("치킨");
//      findMember.getFavoriteFoods().add("한식");
//
//      findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000")); //eqauls 사용
//      findMember.getAddressHistory().add(new AddressEntity("newCity", "street", "10000"));

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
