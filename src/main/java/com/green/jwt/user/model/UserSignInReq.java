package com.green.jwt.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserSignInReq {
    @Schema(title = "이메일", example = "aa@bb", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(title = "비밀번호", example = "cc", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pw;

}
