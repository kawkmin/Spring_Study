package hello.basic.web;

import hello.basic.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  private final ObjectProvider<MyLogger> myLoggerProvider;

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) throws InterruptedException {
    String requestURL = request.getRequestURI().toString();
    MyLogger myLogger = myLoggerProvider.getObject(); //스코프가 최초로 만들어지는 곳
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    Thread.sleep(1000); //새로고침을 여러하면, 다른게 중간에 생성되어도 아이디를 유지하며 닫힘 = 따로 생성확인 가능
    logDemoService.logic("testId");
    return "OK";
  }
}
