package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Controller
//@ResponseBody 2개를 합치면 RestController
@RestController
public class ResponseBodyController {

  @GetMapping("/response-body-string-v1")
  public void responseBodyV1(HttpServletResponse response) throws IOException {
    response.getWriter().write("ok");
  }

  @GetMapping("/response-body-string-v2")
  public ResponseEntity responseBodyV2() {
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  //@ResponseBody
  @GetMapping("/response-body-string-v3")
  public String responseBodyV3() {
    return "ok";
  }

  @GetMapping("/response-body-json-v1")
  public ResponseEntity<HelloData> responseBodyJsonV1() {
    HelloData helloData = new HelloData();
    helloData.setUsername("userA");
    helloData.setAge(20);

    return new ResponseEntity<>(helloData, HttpStatus.OK);
  }

  @ResponseStatus(HttpStatus.OK)
  //@ResponseBody
  @GetMapping("/response-body-json-v2")
  public HelloData responseBodyJsonV2() { // 자주 쓰이는 방식
    HelloData helloData = new HelloData();
    helloData.setUsername("userA");
    helloData.setAge(20);
    return helloData;
  }
}
