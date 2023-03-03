package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestBodyJsonController {

  private ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping("request-body-json-v1")
  public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody={}", messageBody);
    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); //json 데이터 자동 객체로 변환
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    response.getWriter().write("ok");
  }

  @ResponseBody
  @PostMapping("request-body-json-v2")
  public String requestBodyJsonV2(@RequestBody String messageBody)
      throws IOException {

    log.info("messageBody={}", messageBody);
    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

  @ResponseBody
  @PostMapping("request-body-json-v3")
  public String requestBodyJsonV3(
      @RequestBody HelloData helloData) // (RequestBody가 Json요청->Http 컨버터 -> 객체로 반환)
  // 생략하면 ModelAttribute가 되어서 생략 X
  // 기본값 0도 가능 <- 파라미터 때는 null로만 해야했음
  {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

  @ResponseBody
  @PostMapping("request-body-json-v4")
  public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
    HelloData data = httpEntity.getBody();
    log.info("username={}, age={}", data.getUsername(), data.getAge());

    return "ok";
  }

  @ResponseBody
  @PostMapping("request-body-json-v5")
  public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
    log.info("username={}, age={}", data.getUsername(), data.getAge());

    return data; //json으로 출력됨 (ResponseBody가 객체 -> HTTP 컨버터 -> JSON 응답)
  }
}
