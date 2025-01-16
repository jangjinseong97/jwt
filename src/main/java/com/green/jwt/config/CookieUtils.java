package com.green.jwt.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class CookieUtils {

    //Req header 에서 내가 원하는 쿠키를 찾는 메소드
    public Cookie getCookie(HttpServletRequest req, String name) {
        // HttpServletRequest 에서 외부의 Q-S 또는 json 등등 형태로 오는 데이터를 받아서 분류해서
        // controller 로 주는 것
        Optional<Cookie[]> optCookie = Optional.ofNullable(req.getCookies());
        //optional 제작 부분(ofNullable로 null을 가질수 있게 됨)


        return Arrays.stream(optCookie.orElseThrow(() -> new RuntimeException("Cookie not found")))
                // 배열이라 스트림으로 생성(쿠키가 있다면) > Stream<Cookie[]>
                .filter(item -> item.getName().equals(name)) //필터 조건에 맞는 stream을 리턴(중간연산)
                .findAny()
                // findFirst() 를 사용해도 된다. > 첫번째 아이템 선택후 optinal로 리턴 (최종연산)
                .orElseThrow(() -> new RuntimeException("cookie not found"));
                // 위에서 find로 찾았을떄 없었다면 에러를 터트림

    }

    //Res header 에서 내가 원하는 쿠키를 담는 메소드
    public void setCookie(HttpServletResponse res, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/api/user/access-token");
        //이 요청으로 들어올 때만 쿠키 값이 넘어올 수 있도록
        cookie.setHttpOnly(true); // 보안 쿠키 설정, 프론트에서 JS로 쿠키값을 얻을수 없어짐
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }
}
