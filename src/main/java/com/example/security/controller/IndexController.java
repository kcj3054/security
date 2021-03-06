package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //localhost:8080
    //localhost:8080/
    @GetMapping({""})
    public  String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String enPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(enPassword);
        userRepository.save(user); // 회원가입 잘됨 비밃너호 1234 -> 패스워드 암호화해야지 시큐리티 사용 가능

        return "redirect:/loginForm";
    }


    //ROLE_ADMIN만 들어 갈 수 있다
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public String info() {
        return "개인정보";
    }

    //PreAuthorize 권한을 여러개 설정할 경우
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('Role_MANAGER')")
    @GetMapping("/data")
    public String data() {
        return "데이터정보 ";
    }
}
