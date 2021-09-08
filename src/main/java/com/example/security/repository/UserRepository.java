package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


//crud함수를 jpaRepository가 들고있다
//@Repository없어도 ioc가된다 (제어의 역전) 이유는 jpaRepository를 상속했기때문에
public interface UserRepository extends JpaRepository<User, Integer> {


}
