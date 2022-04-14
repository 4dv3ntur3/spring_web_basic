package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 클래스 이름 더블클릭 -> 드래그 됨
// ctrl shift t -> create test

// JPA 쓰려면 항상 서비스 객체 위에 이게 있어야 함
// 데이터 저장이나 변경할 때는 필요함
// 그래서 여기다 해도 되고 회원가입 할 때만 필요하니까 그 메서드 위에 붙여도 됨
@Transactional
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 외부에서 넣어 주도록 변경
    @Autowired
    // 생성자 주입
    // 구현체로 있는 MemoryMemberRepository 서비스에 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*

    public static void main(String[] args) {
        MemberService ms = new MemberSerivce(); // 이렇게 하면 얘도 autowired 작동 안 함
        // 스프링 컨테이너에 올라가는 것만 작동
    }     */

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

        // AOP
        // 시간 찍기
//        long start = System.currentTimeMillis();
//
//        try {
//            validateDuplicateMember(member); // 중복 회원 검증
//            memberRepository.save(member); // 저장
//            return member.getId();
//
//        } finally {
//            // 에러가 나든 안나든 찍어야 함
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 저장
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
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
