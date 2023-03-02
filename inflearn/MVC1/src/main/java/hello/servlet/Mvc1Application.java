package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan //서블릿 자동 등록
@SpringBootApplication
public class Mvc1Application {

  public static void main(String[] args) {
    SpringApplication.run(Mvc1Application.class, args);
  }

/*
  ModelAndView 에서 해줌
  @Bean
  InternalResourceViewResolver internalResourceViewResolver() {
    return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
  }
*/

}
