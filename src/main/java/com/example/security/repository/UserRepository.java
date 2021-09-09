package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


//crud함수를 jpaRepository가 들고있다
//@Repository없어도 ioc가된다 (제어의 역전) 이유는 jpaRepository를 상속했기때문에
public interface UserRepository extends JpaRepository<User, Integer> {


    //findBy는 규칙 -> Username은 문법
    //select * from user where username = 1?
    //?부분에 username이 들어온다
    public User findByUsername(String username);

    //이것은 jpa query methods
    //select * from user where email= ?이 실행된다
    //public User findByEmail();
}
