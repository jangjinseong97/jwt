package com.green.jwt.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.jwt.config.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper;
    private final SecretKey secretKey;
    private final JwtConst jwtConst;

    public JwtTokenProvider(ObjectMapper objectMapper, JwtConst jwtConst) {
        this.objectMapper = objectMapper;
        this.jwtConst = jwtConst;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConst.getSecret()));
    }

    public String generateAccessToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getAccessTokenExpiry());
    }
    public String generateRefreshToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getRefreshTokenExpiry());
    }
    public String generateToken(JwtUser jwtUser,long tokenValidMilliSecond) {
        Date now = new Date();
        return Jwts.builder()
                .header().type(jwtConst.getTokenType())
                .and()
                //header
                .issuer(jwtConst.getIssuer())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidMilliSecond))
                .claim(jwtConst.getClaimKey(), makeClaim(jwtUser))
                //payload
                .signWith(secretKey)
                //signature
                .compact();
    }
    private String makeClaim(JwtUser jwtUser){
        try {
            return objectMapper.writeValueAsString(jwtUser);
            // 직렬화
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    // 객체 자체를 JWT에 담고 싶어 객체를 직렬화(객체가 String이 됨)
    // 즉 jwtUser에 담고 있는 데이터를 Json형태의 문자열로 바꿔줌

    // ----여기 윗부분이 토큰 제작
    // 아래는 제작된 토큰(AT, RT)을 사용
    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public JwtUser getJwtUserFromToken(String token){
        Claims claims = getClaims(token);
        String json = claims.get(jwtConst.getClaimKey(), String.class);
        try {
           return objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    public Authentication getAuthentication(String token) {
        JwtUser jwtUser = getJwtUserFromToken(token);
        return new UsernamePasswordAuthenticationToken(jwtUser,null,jwtUser.getAuthorities());

    }
}
