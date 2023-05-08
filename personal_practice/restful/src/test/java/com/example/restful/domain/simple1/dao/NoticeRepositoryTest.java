package com.example.restful.domain.simple1.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.restful.domain.simple1.entity.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeRepositoryTest {

  @PersistenceContext
  EntityManager em;

  @Autowired
  NoticeRepository noticeRepository;

  @BeforeEach
  public void beforeEach() {
    Notice notice1 = new Notice("gawk", "책 읽지 말자", "con1");
    Notice notice2 = new Notice("kawk", "책 읽자", "con2");
    noticeRepository.save(notice1);
    noticeRepository.save(notice2);
  }

  @DisplayName("모두 잘 저장되는지 확인")
  @Test
  public void when_findAll_should_right() {
    List<Notice> findNotices = noticeRepository.findAll();
    assertThat(findNotices.size()).isEqualTo(2);
  }

}