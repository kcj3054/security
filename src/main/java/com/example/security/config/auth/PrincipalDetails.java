package com.example.security.config.auth;


import com.example.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티가 /login 주소 요청이 오면 낚아채서ㅓ 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다.
// Authentication 안에 User정보가 있어야함
// User오브젝트 타입 => UserDetails 타입객체
//security Session => Authentication => UserDetails(PrincipalDetails)

public class PrincipalDetails implements UserDetails {

    private User user;
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 user의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //우리 사이트!! 1년동안 회원이 로그인을 안하면
        // 휴면 계정으로 하기로함
        // 현재시간 - 로그인시간 -> 1년 초과하면 return false;
        //user.getloginDate();
        return true;
    }
}
