package hello.hellospring.service;
// test 밑에 만들어 줌

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 이게 안 되면 build.gradle에 h2 관련 디펜던시 추가해야
class MemberSerivceIntegrationTest {

    // 이미 존재하는 회원입니다 에러의 경우
    // DB에 들어가 있어서 그렇다! 삭제하고 다시 돌리자

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    //@Commit
    // 이렇게 하면 롤백되지 않고 테스트케이스가 DB에 입력된채로 남아있다
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