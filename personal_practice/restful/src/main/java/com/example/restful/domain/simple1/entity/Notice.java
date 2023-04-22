package com.example.restful.domain.simple1.entity;

import com.example.restful.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String author;
  private String title;
  private String content;

  @Builder
  public Notice(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public void updateAuthor(String author) {
    this.author = author;
  }
}
