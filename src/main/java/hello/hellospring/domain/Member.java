package hello.hellospring.domain;

// jpa를 사용하려면 엔티티를 매핑해야 한다
// jpa = 인터페이스
// 구현체로 하이버네이트 등의 vendor들이 있는 건데 hibernate를 거의 많이 쓴다
// 여러 업체가 있지만 더 쓰기 편하고 그런 게 있는 것
// ORM :  객체 관계형 DB

import javax.persistence.*;

@Entity // javax persistence 거 댕겨와야함
public class Member {
    
    
    // primarty key (PK)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// insert하면 DB가 자동으로 생성해 주는 key임 = DB 자동 생성 = 아이덴티티 전략이라 IDENTITY
    private Long id; // 시스템이 구분을 위해 부여한 아이디

    // 이런 annotation으로 매핑
    // column name에 이 class의 name을 매핑하겠다
    @Column(name="name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
