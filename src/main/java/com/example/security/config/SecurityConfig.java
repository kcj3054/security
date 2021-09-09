package com.example.security.config;

import com.example.security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // 스피링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록딘다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    //해당 메소드의 리던되는 객체를 ioc로 등록된다
    @Bean
    public BCryptPasswordEncoder encodPwd() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN')" +
                "or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()  //권한이 없는 페이지에 요청이 들어갈때 login페이지로 이동
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해주낟

                .defaultSuccessUrl("/")
        //defaultSuccessUrl -> loginForm으로 와서 로그인하면 /으로 이동하는데
        // 특정페이지로 들어가면 그 특정페이지로 이동시켜준다
                .and()
                .oauth2Login()
                //1코드받기(인증) 2. 엑세스토큰(권한) 3 사용자 프로필 정보 가져와서 4. 그 정보를 토대로 회원가입 자동으로 진행

                .loginPage("/loginForm") // 구글 로그인이 되고 후처리를 해야함
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

    }
}
