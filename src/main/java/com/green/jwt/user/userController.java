package com.green.jwt.user;

import com.green.jwt.user.model.UserSignUpReq;
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
public class userController {
    private final userService userService;
    @PostMapping("sign-up")
    public long signUp(@RequestBody UserSignUpReq req){
        log.info("sign-up req: {}", req);
        userService.insUser(req);
        return req.getId();
    }
}
