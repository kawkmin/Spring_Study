package hellojpa;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;
  @Column(name = "USERNAME")
  private String name;

  //  private LocalDateTime startDate;
//  private LocalDateTime endDate;
  @Embedded
  private Period period;

  //  private String city;
//  private String street;
//  private String zipcode;
  @Embedded
  private Address homeAddress;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
      @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))})
  private Address workAddress;

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

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }
}
