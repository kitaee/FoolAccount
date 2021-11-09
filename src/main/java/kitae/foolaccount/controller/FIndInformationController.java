package kitae.foolaccount.controller;

import kitae.foolaccount.domain.Member;
import kitae.foolaccount.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FIndInformationController {

    private final MemberRepository memberRepository;

    @Autowired
    public FIndInformationController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    @GetMapping("/find_id")
    public String find_id(){
        return "find_id";
    }

    @GetMapping("/login_with_id")
    public String login_with_id(){
        return "login_with_id";
    }

    @PostMapping("/find_id")
    public String findIdByInformation(MemberForm form, Model model){
        Member lostMember = new Member();
        lostMember.setName(form.getName());
        lostMember.setPhone(form.getPhone());

        Member findMember1 = new Member();
        Member findMember2 = new Member();

        if(memberRepository.findByName(lostMember.getName()).isPresent()){ // 사용자가 입력한 이름이 DB에 있을 때
            findMember1 = memberRepository.findByName(lostMember.getName()).get();
        }
        else{ // 사용자가 입력한 이름이 DB에 없을 때
            return "find_id_fail";
        }

        if(memberRepository.findByPhone(lostMember.getPhone()).isPresent()){ // 사용자가 입력한 번호가 DB에 있을 때
            findMember2 = memberRepository.findByPhone(lostMember.getPhone()).get();
        }
        else{ // 사용자가 입력한 번호가 DB에 없을 때
            return "find_id_fail";
        }

        if(findMember1.getCount() == findMember2.getCount()){  // 입력한 번호와 이름의 count가 일치할 때
            model.addAttribute("name", findMember1.getName());
            model.addAttribute("id", findMember1.getId());
            return "login_with_id";
        }
        else{ // 입력한 번호와 이름의 count가 불일치할 때
            return "find_id_fail";
        }
    }


    @GetMapping("/find_password")
    public String find_password(){
        return "find_password";
    }

    @PostMapping("/find_password")
    public String findPasswordByInformation(Model model, MemberForm form){
        Member lostMember = new Member();
        lostMember.setId(form.getId());
        lostMember.setPassword_confirm_question(form.getPassword_confirm_question());
        lostMember.setPassword_confirm_question_answer(form.getPassword_confirm_question_answer());

        Member findMember1;
        Member findMember2;

        if(memberRepository.findById(lostMember.getId()).isPresent()){ // 사용자가 입력한 아이디가 DB에 있을 때
            findMember1 = memberRepository.findById(lostMember.getId()).get();
        }
        else{ // 사용자가 입력한 아이디이 DB에 없을 때
            System.out.println("tqtq");
            return "find_password_fail";
        }

        if(memberRepository.findByPassword_confirm_question_answer(lostMember.getPassword_confirm_question_answer()).isPresent()){ // 사용자가 입력한 답변이 DB에 있을 때
            findMember2 = memberRepository.findByPassword_confirm_question_answer(lostMember.getPassword_confirm_question_answer()).get();
        }
        else{ // 사용자가 입력한 답변이 DB에 없을 때
            System.out.println(form.getPassword_confirm_question_answer());
            return "find_password_fail";
        }

        if(findMember1.getCount() == findMember2.getCount()){  // 입력한 아이디와 답변의 count가 일치할 때
            model.addAttribute("name", findMember1.getName());
            model.addAttribute("password", findMember1.getPassword());
            return "login_with_password";
        }
        else{ // 입력한 아이디와 답변의 count가 불일치할 때
            System.out.println("tqtq2");
            return "find_password_fail";
        }
    }


}
