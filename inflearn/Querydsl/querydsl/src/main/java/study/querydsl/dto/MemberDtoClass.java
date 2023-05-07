package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDtoClass {

  private String username;
  private int age;

  @QueryProjection // DTO도 Q파일 생성 & 아키텍쳐 관점 문제 : querydsl 영향을 가짐
  public MemberDtoClass(String username, int age) {
    this.username = username;
    this.age = age;
  }

  public MemberDtoClass() {
  }
}
