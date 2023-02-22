package hello.basic.web;

import hello.basic.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

  private final MyLogger myLogger;

  public void logic(String testId) {
    System.out.println("testId = " + testId);
  }
}
