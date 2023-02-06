package hello.basic.member;

public interface MemberRepository {

  void save(Member member);

  Member findByID(Long memberId);
}
