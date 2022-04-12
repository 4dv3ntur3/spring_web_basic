package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

// 클래스 이름 더블클릭 -> 드래그 됨
// ctrl shift t -> create test
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 외부에서 넣어 주도록 변경
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // /** 만 치면 알아서 양식 생김

    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원은 안 됨
        // Optional 바로 반환하는 게 좋지는 않음 안 예쁨
//        Optional<Member> result = memberRepository.findByNmae(member.getName());
//        result.ifPresent(m -> {
//            // null이 아니면 동작
//            // 기존에는 result.get() 한 다음에 if != null 이런 식으로 했음
//            // Optional이라 가능함함
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });


        // 권장
        // 그런데 이렇게 쭉 로직이 나오는 경우에는 그냥 메서드로 뽑는 게 좋음
        // 드래그 후 우클릭 -> refactoring -> extract method
//        memberRepository.findByNmae(member.getName())
//                .ifPresent(m ->  {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 저장
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByNmae(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                    // exception 터지나 확인하는 것?
                    // 메인 메서드 만들고 controller 연결해서 페이지로 보고 할 수도 있겠지만
                    // test 작성!
                });
    }

    /**
     * 전체 회원 조회
     *
     * @return
     */

    // 리포지토리는 단순히 넣었다 뺐다 하는 느낌. 기계적으로.
    // 서비스는 약간 비즈니스에 가까움... join 이런 용어 써야 함
    // 그래야 문제 생겼을 때 찍어서 가보기도 쉬움
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);

    }
}
