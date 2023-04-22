package com.example.restful.domain.simple1.dto.response;

import com.example.restful.domain.simple1.entity.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeResponse {

  private String author;
  private String title;
  private String content;

  public NoticeResponse(Notice notice) {
    this.author = notice.getAuthor();
    this.title = notice.getTitle();
    this.content = notice.getContent();
  }
}
