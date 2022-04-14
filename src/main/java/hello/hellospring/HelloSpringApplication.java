package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 이걸 실행시키면 하위 패키지를 다 찾아서 자동으로 등록해줌
// 아니면 등록이 안 됨.. .방법이 있긴 한데 (컴포넌트 스캔 대상이 아님)
public class HelloSpringApplication {

	// 메인 메서드
	public static void main(String[] args) {

		// 이 매개변수에 클래스 이름을 넣어준 것
		// 톰캣 내장하고 있기 때문에 자체적으로 띄우면서 스프링부트도 올라온다
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}

// 인텔리제이 빌드 -> 자바로 실행되지 않고 gradle 통해서 실행되기도 함
//