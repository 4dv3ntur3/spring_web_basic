package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;


/*

개발 후 test 작성
뒤집어서 test class 작성한 다음 만들 수도 있음
검증틀 먼저 만들기
별 만들 거면 미리 별 틀을 만들어 놓고 작품이 완성되면 꽂아 보는 것
테스트 주도 개발 -> TBD

테스트가 수십 수백개면
빌드
repository 패키지 위에 run test in hello.hellospring -> 이렇게 해봐도 됨
gradledw test 이렇게 해봐도 됨
다 자동으로 돌려줌
테스트 코드 없이 개발? 불가능... 문제 많이 생김 
꼭 깊이 있게 공부할 것

 */




class MemoryMemberRepositoryTest {

    // class level에서 돌리면 전부 다 돌려 볼 수 있음
    // test 디렉토리에서 돌리면 모든 클래스 다 테스트 가능
    // 그런데 클래스 단위로 실행하면 뭐가 먼저 실행될지 알 수 없음.
    // test 하나 끝나고 나면 데이터 clear해야 함!

    //MemoryMemberRepository만 확인하는 거니까 일단 똑같이 맞춤
//    MemberRepository repository = new MemoryMemberRepository();
    MemoryMemberRepository repository = new MemoryMemberRepository();


    @AfterEach
    // 메서드 하나 끝날 떄마다 실행
    // 테스트는 서로 의존관계 없이, 순서에 상관없이 설계가 되어야 함
    // 한 테스트가 끝날 떄마다 공용 데이터 깔끔하게 지워 줘야 함
    public void afterEach() {
        repository.clearStore();
    }


    @Test
    public void save() {
        // 얘를 실행함
        // 메인메서드 돌리는 거랑 유사함
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

//        repository.findById(member.getId());

        // optional에서 값을 꺼낼 때는 get으로
        // 바로 꺼내는 게 좋은 건 아닌데 테스트 코드니까 괜찮음
        Member result = repository.findById(member.getId()).get();
        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(result, member); // 똑같은지 확인 -> 안 맞으면 오류

        // 최근엔 이걸 씀
        // org.assertj.core.api
        // add-on static 어쩌고 하면 그냥 assertThat으로 바로 쓸 수 있음
        Assertions.assertThat(member).isEqualTo(result);
        // 빌드 툴이랑 엮어서 많이 씀 못 넘어가게
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // optional에서 까서 꺼내기
        Member result = repository.findByNmae("spring2").get();
        Assertions.assertThat(result).isEqualTo(member1);



    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // optional에서 까서 꺼내기
        List<Member> result = repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);



    }





}
