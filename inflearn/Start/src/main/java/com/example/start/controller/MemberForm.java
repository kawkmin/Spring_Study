package com.example.start.controller;

public class MemberForm {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) { //post를 통해 spring이 setName()을 호출함
    this.name = name;
  }
}
