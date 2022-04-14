package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//Repository 인터페이스의 구현체

public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 문제가 있어서 concurrent? hash를 써야 하는데 일단 이렇게 함
    private static Map<Long, Member> store = new HashMap<>();

    // 실무에서도 동시성 문제 고려를 해야 하지만 여기선 단순하게
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 시스템이 부여한 것
        store.put(member.getId(), member); // 고객이 정한 이름
        // map에 저장됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // null이 반환될 가능성이 있으면 이렇게 감싸서 반환
        // 그러면 client에서 뭘 할 수 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 람다식
        // 찾으면 반환
        // 결과 optinal로 반환됨
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 하나라도 찾기

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // member들이 반환됨
    }

    // 이렇게 작성해놓고 동작하는지 어떻게 확인하지?
    // test case!


    public void clearStore() {
        store.clear();
    }
}
