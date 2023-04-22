package com.example.restful.domain.simple1.application;

import com.example.restful.domain.simple1.dao.NoticeRepository;
import com.example.restful.domain.simple1.dto.request.NoticeRequest;
import com.example.restful.domain.simple1.entity.Notice;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

  private final NoticeRepository noticeRepository;

  @Transactional
  public long save(NoticeRequest request) {
    Notice notice = request.toEntity();
    return noticeRepository.save(notice).getId();
  }

  public Notice findOne(Long id) {
    return noticeRepository.findById(id).get();
  }

  public List<Notice> findAll() {
    return noticeRepository.findAll();
  }

  @Transactional
  public void update(Long id, String author) {
    Notice notice = findById(id);
    notice.updateAuthor(author);
  }

  @Transactional
  public void delete(Long id) {
    noticeRepository.delete(findById(id));
  }

  private Notice findById(Long id) {
    return noticeRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException());
  }

}
