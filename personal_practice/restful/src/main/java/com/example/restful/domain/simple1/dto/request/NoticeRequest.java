package com.example.restful.domain.simple1.dto.request;

import com.example.restful.domain.simple1.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NoticeRequest(
    @NotNull String author,
    @NotBlank(message = "title은 필수입니다") String title,
    @NotNull String content
) {

  public Notice toEntity() {
    return Notice.builder()
        .author(author)
        .title(title)
        .content(content)
        .build();
  }
}
