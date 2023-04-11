package study.datajpa.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

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

  List<Member> findListByUsername(String username);

  Member findMemberByUsername(String username);

  Optional<Member> findOptionalByUsername(String username);

  // 3.0 버전에선 상관없나? 결과가 같음..
  //@Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
  Page<Member> findByAge(int age, Pageable pageable);
  //Slice<Member> findByAge(int age, Pageable pageable);

  @Modifying(clearAutomatically = true) //executeUpdate 역할
  @Query("update Member m set m.age = m.age +1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  @Query("select m from Member m left join fetch m.team")
  List<Member> findMemberFetchJoin();

  @Override
  @EntityGraph(attributePaths = {"team"})
  List<Member> findAll();

  @EntityGraph(attributePaths = {"team"})
  @Query("select m from Member m")
  List<Member> findMemberEntityGraph();

  @EntityGraph(attributePaths = ("team"))
    //@EntityGraph("Member.all") //Entity에서 설정
  List<Member> findEntityGraphByUsername(@Param("username") String username);

  @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
  Member findReadOnlyByUsername(String username);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Member> findLocksByUsername(String username);
}
