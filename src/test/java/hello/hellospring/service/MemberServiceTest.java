package hello.hellospring.service;
// test 밑에 만들어 줌

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

// shift + f10 -> 이전에 실행했던 것 실행
class MemberServiceTest {


    // 외부에서 memberService 생성하면서 memberRepository 하도록 만들었더니 여기 오류가 나네
//    MemberService memberService = new MemberService();

    MemberService memberService;
    MemoryMemberRepository memberRepository;


    // clear를 해 줘야 하는데
    // MemberService뿐임
    // 클래스 데이터 타입에 ctrl + 클릭 -> 정의 피킹

    // 다른 객체가 생성이 되면 다른 인스턴스라서 내용물이 달라질 수도 있음
    // 그니까 클래스에서 쓰는 거랑 test에서 쓰는 거랑 new로 생성했기 때문에 서로 다른 인스턴스임
    //

//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    // MemberService 입장에서 봤을 때 memberRepository를 외부에서 넣어 줌
    // dependency Injection

    // 각 테스트 실행 전에
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    // test는 이렇게 해도 됨
    // 직관적이기 때문에...
    // 빌드할 때 테스트 코드는 실제 코드에 포함 안 됨
    // 기본적으로 given-when-then 패턴
    void 회원가입() {
        // given: 이런 상황 주어짐
        Member member = new Member();
        member.setName("spring"); // 이러면 AfterEach 없이는 계속 중복가입이라고 예외처리 나옴

        // when: 이걸로 실행했을 때
        // join 검증
        // 저장한 아이디가 return되도록 했음 (스펙상)
        Long saveId = memberService.join(member);

        // then: 이렇게 나와야 됨
        // 저장된 아이디의 정보
        Member findMember = memberService.findOne(saveId).get();

        // 멤버 이름이 findMember의 이름과 같은가?
        // assertThat에 커서 두고 alt + enter -> static import 선택
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 예외 처리가 더 중요함!!!
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        // 두 번쨰 join에서 예외가 나야 함
        memberService.join(member1);

        /*
        try {
            memberService.join(member2);
            // exception으로 안 가고 이 다음줄이 실행되면 fail임
            // 중복 검사가 제대로 안됐단 얘기니까
//            fail("예외가 발생해야 합니다.");
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        }

         */

        // -> 뒤에 있는 로직을 실행할 건데 앞에 있는 excepton이 터져야 한다
        //
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}