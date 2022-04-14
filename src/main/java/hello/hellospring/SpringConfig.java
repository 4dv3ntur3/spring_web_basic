package hello.hellospring;

import hello.hellospring.aop.TimeTraceAOP;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

// java 파일로 컨테이너에 빈 등록
@Configuration
// spring이 뜰 때 이 configuration 읽고
public class SpringConfig {

//    private DataSource dataSource; // for jdbc template
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // for jpa
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }


    // for spring data jpa

    private final MemberRepository memberRepository;

    // 어라 근데 등록한 memberRepository가 없는데? -> SpringDataJpaMemberRepository가 있음
    // 인터페이스 만들고 extend jpa 어쩌고 해 놓으면 지가 구현체를 만들어냄 -> 스프링 빈에 등록
    // injection 가능
    @Autowired // 생략 가능. 생성자가 1개니까
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    // 서비스랑 레포지토리 둘 다 등록한 다음
    // 레포지토리를 멤버 서비스에 넣어줌
    @Bean
    public MemberService memberService() {
        // 이거는 스프링 빈에 등록하라는 뜻이네 하고 등록함
        // 메서드 호출!
//        return new MemberService(memberRepository());


        // spring data jpa
        return new MemberService((memberRepository)); // 밑에 public MemberRepository memberRepository() 이거 주석처리해 야함


    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

    // for jdbc template and jpa
//    @Bean
//    public MemberRepository memberRepository() {
//        // return new MemoryMemberRepository();
////        return new JdbcTemplateRepository(dataSource);
////        return new JpaMemberRepository(em); // jpa
//
//    }

    // ComponentScan annotation 없었으면 bean 등록 방식으로 얘를 살려야 함
//    @Bean
//    public TimeTraceAOP timeTraceAOP() {
//        return new TimeTraceAOP();
//    }
}
