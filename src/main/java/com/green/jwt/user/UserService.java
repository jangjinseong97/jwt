package com.green.jwt.user;

import com.green.jwt.config.CookieUtils;
import com.green.jwt.config.JwtConst;
import com.green.jwt.config.jwt.JwtTokenProvider;
import com.green.jwt.config.jwt.JwtUser;
import com.green.jwt.user.model.UserSelOne;
import com.green.jwt.user.model.UserSignInReq;
import com.green.jwt.user.model.UserSignInRes;
import com.green.jwt.user.model.UserSignUpReq;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TransactionTemplate transactionTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final JwtConst jwtConst;
    //    @Transactional
    public void insUser(UserSignUpReq req) {
        String hashPw = passwordEncoder.encode(req.getPw());
        req.setPw(hashPw);

        //원하는 부분만 주고 싶다면
        transactionTemplate.execute(status -> {
            userMapper.insUser(req);
            userMapper.insUserRole(req);
            return null;
        });
    }
    public UserSignInRes signIn(UserSignInReq req , HttpServletResponse response) {
        UserSelOne userSelOne = userMapper.selUserWithRoles(req).orElseThrow(() -> {
            throw new RuntimeException("아이디 확인");
        });
        if(!passwordEncoder.matches(req.getPw(), userSelOne.getPw())) {
            throw new RuntimeException("비밀번호 확인");
        }
        JwtUser jwtUser = new JwtUser(userSelOne.getId(), userSelOne.getRoles());
        String accessToken = jwtTokenProvider.generateAccessToken(jwtUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(jwtUser);

        // RT를 쿠키에 담는법
        cookieUtils.setCookie(response,jwtConst.getRefreshTokenCookieName(),refreshToken,
                jwtConst.getRefreshTokenCookieExpiry());

        return UserSignInRes.builder()
                .id(userSelOne.getId())
                .accessToken(accessToken)
                .name(userSelOne.getName())
                .build();
    }
}
