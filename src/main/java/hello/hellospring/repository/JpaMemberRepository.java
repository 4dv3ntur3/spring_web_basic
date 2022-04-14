package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

// PK 기반이 아닌 기능들 (findByName, findByAll 등은 JPQL 작성해야 함
public class JpaMemberRepository implements MemberRepository{

    // jpa는 엔티티 매니저로 모든 게 동작
    // build.gradle에서 data-jpa 라이브러리 받았음
    // 스프링부트가 자동으로 entitymanager 생성해 줌
    // 만들어 준 거 injection만 하면 됨 (DB 커넥션정보랑 옵션이랑 그런 거 다 짬뽕해서 만들어 줌)
    private final EntityManager em;


    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 영구 저장하다.. 뭐 그런 뜻
        // set id도 다 해 줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 조회할 TYPE이랑 식별자, pk값
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // 있을 수도 있고 없을 수도 있기 때문에
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 조회 저장 삭제 등은 SQL 짤 필요 없다.. 자동으로 다 됨
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    
    public List<Member> findAll() {
//         특별한 jpql 이라는 객체지향 언어 사용해야
//        List<Member> result = em.createQuery("select m from Member m", Member.class)
//                      .getResultList();
//
//        return result;

        // 위의 거 inline alt shift enter
        // inline variable
        // 객체 대상으로 쿼리를 날린다. member entity를 조회해
        // member entity 자체를 고른다. sql만 쓸 때는... m.id, m.name 및 매핑...
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();




    }
}
