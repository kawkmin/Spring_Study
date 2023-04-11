package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  List<Member> findTop3HelloBy(); //Hello는 쿼리X

  //@Query(name = "Member.findByUsername") // 우선순위 1.Member에 named 쿼리랑 같은 메소드 명 2.메소드 명 쿼리
  List<Member> findByUsername(@Param("username") String username); //이름 상관 X

  // 오타를 컴파일 에러로 잡아줌
  @Query("select m from Member m where  m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select m.username from Member m")
  List<String> findUsernameList();

  @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") Collection<String> names);
}
