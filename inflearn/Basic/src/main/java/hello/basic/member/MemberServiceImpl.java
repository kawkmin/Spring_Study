package hello.basic.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findByID(memberId);
  }

  //테스트 용도
  public MemberRepository memberRepository() {
    return memberRepository;
  }
}
