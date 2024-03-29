package hello.springtx.propagation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@Slf4j
@SpringBootTest
class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  LogRepository logRepository;

  /**
   * @Transactional:OFF
   * @Transactional:ON
   * @Transactional:ON
   */
  @Test
  void outerTxOff_success() {
    String username = "outerTxOff_success";

    memberService.joinV1(username);

    Assertions.assertTrue(memberRepository.find(username).isPresent());
    Assertions.assertTrue(logRepository.find(username).isPresent());

  }

  /**
   * @Transactional:OFF
   * @Transactional:ON
   * @Transactional:ON Exception
   */
  @Test
  void outerTxOff_fail() {
    String username = "로그예외_outerTxOff_fail";

    assertThatThrownBy(() -> memberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    Assertions.assertTrue(memberRepository.find(username).isPresent());
    Assertions.assertTrue(logRepository.find(username).isEmpty());

  }

  /**
   * memberService @Transactional:On
   * <p>
   * memberRepository @Transactional:OFF
   * <p>
   * LogRepository @Transactional:OFF
   */
  @Test
  void singleTx() {
    String username = "outerTxOff_success";

    memberService.joinV1(username);

    Assertions.assertTrue(memberRepository.find(username).isPresent());
    Assertions.assertTrue(logRepository.find(username).isPresent());

  }

  /**
   * memberService @Transactional:On
   * <p>
   * memberRepository @Transactional:ON
   * <p>
   * LogRepository @Transactional:OM
   */
  @Test
  void outerTxOn_success() {
    String username = "outerTxON_success";

    memberService.joinV1(username);

    Assertions.assertTrue(memberRepository.find(username).isPresent());
    Assertions.assertTrue(logRepository.find(username).isPresent());
  }

  /**
   * @Transactional:ON
   * @Transactional:ON
   * @Transactional:ON Exception
   */
  @Test
  void outerTxON_fail() {
    String username = "로그예외_outerTxON_fail";

    assertThatThrownBy(() -> memberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    Assertions.assertTrue(memberRepository.find(username).isEmpty());
    Assertions.assertTrue(logRepository.find(username).isEmpty());

  }

  /**
   * @Transactional:ON
   * @Transactional:ON
   * @Transactional:ON Exception
   */
  @Test
  void recoverException_fail() {
    String username = "로그예외_recoverException_fail";

    assertThatThrownBy(() -> memberService.joinV2(username))
        .isInstanceOf(UnexpectedRollbackException.class);

    Assertions.assertTrue(memberRepository.find(username).isEmpty());
    Assertions.assertTrue(logRepository.find(username).isEmpty());

  }

  /**
   * @Transactional:ON
   * @Transactional:ON
   * @Transactional:ON(REQUIRES_NEW) Exception
   */
  @Test
  void recoverException_success() {
    String username = "로그예외_recoverException_success";

    memberService.joinV2(username);

    Assertions.assertTrue(memberRepository.find(username).isPresent());
    Assertions.assertTrue(logRepository.find(username).isEmpty());

  }
}