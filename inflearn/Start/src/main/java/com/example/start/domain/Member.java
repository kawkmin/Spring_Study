package com.example.start.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.annotation.processing.Generated;

@Entity // JPA가 관리한다는 뜻
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // PK / DB가 알아서 생성(id가 중복 없게끔)
  private Long id;

  private String name;

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
}
