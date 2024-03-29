package hellojpa;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 범용적으로 추천
@DiscriminatorColumn//(name="DTYPE")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 단순할 때 추천
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)//class로 찾을때 비효율 추천X
public abstract class Item {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private int price;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
