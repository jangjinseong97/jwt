package com.green.jwt.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class JwtUser implements UserDetails {
    private final long signedUserId;
    private final List<UserRole> roles; // 인가(권한)처리 >> 인증은 로그인만 관련 인가는 권한이 어디까지인지

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;

        return roles.stream() //List<String> 을 stream<List<String>>으로 변환
//                .map(new Function<String, SimpleGrantedAuthority>() {
//                    @Override
//                    public SimpleGrantedAuthority apply(String item){
//                        return new SimpleGrantedAuthority(item);
//                    }
//                })
        //map은 똑같은 size의 stream을 만듬, stream<List<SimpleGrantedAuthority>>으로 변환
        // 여기서 SimpleGrantedAuthority::new 는 메소드 참조라고 호칭

         .map(item -> new SimpleGrantedAuthority(item.name())) //이거나
//                .map(SimpleGrantedAuthority::new) 이걸로 작성한것과 동일
                // 코드 수정으로 정확히는 new 부분이 name이 되어야 되는것
                .toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
    //ROLE_이름, ROLE_USER, ROLE_ADMIN
    // 예시인거지 사실 다른걸 담아도 되긴함

}

