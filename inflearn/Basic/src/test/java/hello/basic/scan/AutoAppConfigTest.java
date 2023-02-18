package hello.basic.scan;

import static org.assertj.core.api.Assertions.*;

import hello.basic.AutoAppConfig;
import hello.basic.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        AutoAppConfig.class);

    MemberService memberService = ac.getBean(MemberService.class); // 클래스명의 맨 앞글자를 소문자로 해서 사용
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

}
