package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  EntityManager em;
  @Autowired
  OrderService orderService;
  @Autowired
  OrderRepository orderRepository;

  @Test
  public void 상품주문() {
    Member member = createMember();

    Item book = createBook("시골_JPA", 10000, 10);

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    Order getOrder = orderRepository.findOne(orderId);

    assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
    assertThat(1).isEqualTo(getOrder.getOrderItems().size());
    assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice());
    assertThat(8).isEqualTo(book.getStockQuantity());
  }

  @Test
  public void 상품주문_재고수량초과() {
    Member member = createMember();

    Item book = createBook("시골_JPA", 10000, 10);

    int orderCount = 11;

    assertThatThrownBy(
        () -> orderService.order(member.getId(), book.getId(), orderCount)).isInstanceOf(
        NotEnoughStockException.class);
  }

  @Test
  public void 주문취소() {
    Member member = createMember();
    Item book = createBook("시골 JPA", 10000, 10);

    int orderCount = 2;

    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    orderService.cancelOrder(orderId);

    Order getOrder = orderRepository.findOne(orderId);
    assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(book.getStockQuantity()).isEqualTo(10);
  }

  private Item createBook(String name, int price, int stockQuantity) {
    Item book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember() {
    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("서울", "강가", "123-123"));
    em.persist(member);
    return member;
  }
}
