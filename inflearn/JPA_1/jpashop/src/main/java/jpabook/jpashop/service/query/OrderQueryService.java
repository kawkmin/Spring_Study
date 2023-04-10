package jpabook.jpashop.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderQueryService {

  // transactional 안에 모든 로직 들어있으면, OSIV 끄고 가능
//  public List<OrderDto> ordersV3() {
//    List<Order> orders = orderRepository.findAllWithItem();
//
//    return orders.stream()
//        .map(o -> new OrderDto(o))
//        .collect(toList());
//  }

}
