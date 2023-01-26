package com.example.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model) {
    model.addAttribute("data", "hello!!");//key = "data" value="hello!!"
    return "hello";
  }

  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam(name = "name") String name,
      Model model) { //@RequestParam() 주소 뒤에 ? 값으로 들어오는 속성
    model.addAttribute("name", name);
    return "hello-template";
  }

  @GetMapping("hello-string")
  @ResponseBody // view에 보내지 않는다. (viewResolver 사용 x)
  public String helloString(@RequestParam("name") String name) {
    return "hello " + name; // 소스로 보면 그대로 있다.
  }

  @GetMapping("hello-api")
  @ResponseBody
  public Hello helloApi(@RequestParam("name") String name) {
    Hello hello = new Hello(); //객체를 넘기면 default로 Json으로 보낸다
    hello.setName(name);
    return hello;
  }

  static class Hello {

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

}
