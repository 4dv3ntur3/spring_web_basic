package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 웹 어플리케이션에서 /hello 라고 들어오면 이 메서드 호출됨 0
    @GetMapping("hello")
    public String hello(Model model) {

        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String hellomvc(@RequestParam("name") String name, Model model) {
        // key-value
        model.addAttribute("name", name);
        return "hello-template";

    }

    @GetMapping("hello-string")
    @ResponseBody // html의 body 부에 응답을 직접 넣어주겠다 
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // name을 kim이라고 넣으면 hello kim으로 들어가겠지 
        // 이 문자가 그대로 내려감
        // return <html> 어쩌고 해서 페이지를 줄 수도 있겠지만... 굳이?
    }

    // 문자가 아니라 데이터를 내놓으라고 한다면?
    // name: spring!!!
    // json임 (key-value 구조)
    // XML도 있긴 한데 최근에는는 son으로 통일함
    // json 반환이 기본 세팅임
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        // ctrl shift 엔터 치면 완성
        Hello hello = new Hello();
        hello.setName(name);

        // 문자 아닌 객체 넘김
        // json처럼 뜬다
        return hello;

    }

    // java bean 표준 방식
    // getter & setter
    // property 접근 방식
    static class Hello {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

    }

}
