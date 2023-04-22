package com.example.restful.domain.simple1.api;

import com.example.restful.domain.simple1.application.NoticeService;
import com.example.restful.domain.simple1.dto.request.NoticeRequest;
import com.example.restful.domain.simple1.dto.response.NoticeResponse;
import com.example.restful.domain.simple1.entity.Notice;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping("/create")
  public Long createNotice(@Valid @RequestBody NoticeRequest request) {
    return noticeService.save(request);
  }

  @GetMapping
  public List<Notice> getAllNotices() {
    return noticeService.findAll();
  }

  @GetMapping("/{noticeId}")
  public NoticeResponse getNotice(@PathVariable Long noticeId) {
    return new NoticeResponse(noticeService.findOne(noticeId));
  }

}
