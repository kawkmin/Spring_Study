package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  //@NotEmpty //saveMemberV1을 쓰면 Entity를 변경해야함
  private String name;

  @Embedded //생략 가능하지만, 둘 다 써주자
  private Address address;

  // @JsonIgnore //membersV1을 쓰면 Entity를 변경해야함
  @JsonIgnore
  @OneToMany(mappedBy = "member")
  private List<Order> oreders = new ArrayList<>();
}
