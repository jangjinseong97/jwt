package com.green.jwt.user;

import com.green.jwt.user.model.UserSignInReq;
import com.green.jwt.user.model.UserSignInRes;
import com.green.jwt.user.model.UserSignUpReq;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping("sign-up")
    public long signUp(@RequestBody UserSignUpReq req){
        log.info("sign-up req: {}", req);
        userService.insUser(req);
        return req.getId();
    }

    @PostMapping("sign-in")
    public UserSignInRes signIn(@RequestBody UserSignInReq req, HttpServletResponse response){
        log.info("sign-in req: {}", req);
        return userService.signIn(req,response);
    }
}
