package hellojpa;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

  private String city;
  private String street;
  private String zipcode;

  public Address() {
  }

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
