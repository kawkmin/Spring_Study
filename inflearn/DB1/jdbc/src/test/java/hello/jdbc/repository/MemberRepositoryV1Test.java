package hello.jdbc.repository;

import static hello.jdbc.collection.ConnectionConst.PASSWORD;
import static hello.jdbc.collection.ConnectionConst.URL;
import static hello.jdbc.collection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
class MemberRepositoryV1Test {

  MemberRepositoryV1 repository;

  @BeforeEach
  void beforeEach() {
    //기본 DriverManager - 항상 새로운 커넥션을 획득
    //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    //커넥션 풀링
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPoolName(PASSWORD);

    repository = new MemberRepositoryV1(dataSource);
  }

  @Test
  void crud() throws SQLException {

    //save
    Member member = new Member("memberV102", 1000);
    repository.save(member);

    //findById
    Member findMember = repository.findById(member.getMemberId());
    log.info("findMember={}", findMember);
    assertThat(findMember).isEqualTo(member);

    //update: money: 1000->2000
    repository.update(member.getMemberId(), 2000);
    Member updateMember = repository.findById(member.getMemberId());
    assertThat(updateMember.getMoney()).isEqualTo(2000);

    //delete
    repository.delete(member.getMemberId());
    assertThatThrownBy(() -> repository.findById(member.getMemberId()))
        .isInstanceOf(NoSuchElementException.class);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}