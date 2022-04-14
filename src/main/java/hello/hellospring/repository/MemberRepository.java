package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

    // optional; java 8 에 들어간 기능
    // findbyname, findbyname이 null을 반환할 수도 있음
    // optional로 감싸서 반환


}
