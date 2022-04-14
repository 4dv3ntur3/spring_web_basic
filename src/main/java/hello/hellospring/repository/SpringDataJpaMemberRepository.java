package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// interface가 interface를 받을 때는 extends (상속)
// 인터페이스는 다중 상속 가능
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 구현할 게 없음
    // 엥????? 다른 메서드는 어디 갔누
    // 이렇게 jparepository를 받고 있으면 spring data jpa가 구현체를 만들어서 등록해 줌
    // 우리는 가져다 쓰기만 하면 된다
    @Override
    Optional<Member> findByName(String name);
    // 규칙이 있음
    // 이러면 select m from Member m where m.name = ? 라고 jpa가 sql 짜 줌 (JPQL) -> 실행
    // findByNameAndId 도 있고 or도 있고 등등등
    // 인터페이스 이름만으로 개발이 끝난다

}