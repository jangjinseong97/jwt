package com.green.jwt.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 메소드들이 빈등록 되었을 가능성이 높음
// (메소드들도 빈등록해서 사용하고 싶어서 넣는 에노테이션이므로)
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 비밀번호인코더를 빈등록
        return new BCryptPasswordEncoder();
    }
    @Bean
    // 스프링이 메소드 호출을 하고 리턴한 객체의 주소값을 관리 (빈등록)
    // 즉 리턴한게 빈등록 되어 있는것
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 시큐리티가 세션을 사용하지 않는걸 의미

                // 쳐피 화면을 만들지 않아서 SSR이 아니므로 필요 없는부분을 꺼서 리소스확보를 하는 것
                .httpBasic(h -> h.disable())
                //SSR(Server Side Rendering)이 아니다.
                //화면을 만들지 않을거라 비활성화 시킨것 >> 시큐리티 로그인창이 나타나지 않게됨
                .formLogin(from -> from.disable())
                //폼로그인 기능 자체를 비활성화(SSR이 아니라서)
                .csrf(csrf -> csrf.disable())
                // 보안관련 SSR 이 아니면 보안이슈가 없기에 기능을 끄는 것
                .build();
    }

}
