package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
// 컴포넌트 스캔 하고 autowired로
public class MemberController {

    // spring이 뜰 때 container라는 통이 생김
    // 이 controller라는 어노테이션이 있으면 MemberController라는 객체를 생성해서 통에 넣어두고 스프링이 관리함
    // 스프링 빈이 관리된다

    // 2. 필드 주입
    // 별로 안 좋음... 중간에 바꿔치기 할 수 있는 방법이 없음
    // 스프링 뜰 떄 넣어 주기만 하고...
    // @Autowired private final MemberService memberService;

    private MemberService memberService;
    // 별 기능 없음.. 여러개 생성할 필요 x
    // 하나 생성하고 여러 서비스 객체가 공유
    // 따라서 하나 등록!

     //1. 생성자 주입
    @Autowired
    // 스프링이 뜰 때 이 멤버컨트롤러가 생성됨 -> 생성자 호출됨 -> Autowired가 있으면 멤버서비스를 가져다 연결시
    // dependency injection
    // 오 너 멤버서비스가 필요하구나 -> 딱 넣어줌
    public MemberController(MemberService memberService) {

        this.memberService = memberService;

        // aop
        System.out.println("memberService = " + memberService.getClass());
        // 이러면 $$Enhance 어쩌고저쩌고
        // 멤버 서비스를 복제해서 코드를 조작하는 기술을 쓰는 것임...
        
    }

    // 3. setter 주입
    // 생성되고 setter 추후 호출되어 들어감
    // 누군가가 멤버 컨트롤러 호출했을 떄 setMemberSerivce가 public으로 열려 있어야 해서
    // 중간에 바꿀 수가 있다....
    // memberService.setMemberService() 이렇게 바꿀 수가 있다
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("/members/new") // home.html에서 회원 가입 누르면 /members/new/로 href 걸려 있음
    public String createForm() {
        return "members/createMemberForm"; // 이 템플릿 띄움 = html 뿌려짐 (뷰리졸버가 선택)
        // url에 직접 치는 게 GET 방식
    }

    // form 태그에서 등록 -> post 방식으로
    // input 태그의 name 속성이 key가 된다 (서버 넘어갈 때)
    // 등록 버튼 누르면 action url로 POST 방식으로 넘어옴 -> 컨트롤러의 postmapping
    // get: 조회 post: 데이터 등록
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        // Memberform의 name에 아까 내가 친 spring이 들어오게 됨
        // spring이 setName 호출해서 설정해 줌

        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        
        return "redirect:/"; // home으로 돌려보냄
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }


}
