package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // return 된 String 으로 뷰를 찾지 않는다.
@Slf4j
public class LogTestController {

  //private final Logger log = LoggerFactory.getLogger(getClass()); //LogTestController.class도 가능

  @RequestMapping("/log-test")
  public String logTest() {
    String name = "Spring";

    System.out.println("name = " + name);

    log.trace(" trace my log = " + name); // 연산을 먼저 한 후 trace 출력 안함

    log.trace("trace log = {}", name); // trace 라서 출력 안함

    log.debug("debug log = {}", name);
    log.info(" info log = {}", name);
    log.warn("warn log = {}", name);
    log.error("error log = {}", name);

    return "ok";
  }
}
