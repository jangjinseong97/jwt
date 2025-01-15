package com.green.jwt.user;

import com.green.jwt.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class userService {
    private final userMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void insUser(UserSignUpReq req){
        String hashPw = passwordEncoder.encode(req.getPw());
        req.setPw(hashPw);
        userMapper.insUser(req);
        userMapper.insUserRole(req);
    }
}
