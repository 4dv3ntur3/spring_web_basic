package hello.hellospring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 이걸 써야 aop로 쓸 수 있음
@Component // 이렇게 하면 component scan
// configuration에 spring bean으로 등록해서 쓰는 걸 더 선호함
public class TimeTraceAOP {

//    @Around("execution(* hello.hellospring..*(..))")
    @Around("execution(* hello.hellospring.service..*(..))")

    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString()); // 이 안에 어떤 메서드 콜했는지 이름을 다 얻을 수 있음
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
