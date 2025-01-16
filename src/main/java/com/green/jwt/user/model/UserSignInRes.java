package com.green.jwt.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserSignInRes {
    private long id;
    private String name;
    private String accessToken;
}
