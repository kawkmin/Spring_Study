package hello.basic.web;

import hello.basic.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  private final MyLogger myLogger;

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) {
    String requestURL = request.getRequestURI().toString();
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    logDemoService.logic("testId");
    return "OK";
  }
}
