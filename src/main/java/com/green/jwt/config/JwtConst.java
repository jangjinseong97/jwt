package com.green.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt-const")
@RequiredArgsConstructor // final 붙은 얘들만 받겠다는 뜻
@ToString
public class JwtConst {
    private final String issuer;
    private final String secret;
    private final String headerSchemaName;
    private final String tokenType;
    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;
    private final String refreshTokenCookieName;
    private final int refreshTokenCookieExpiry;
    private final String tokenName;
    private final String claimKey;
}
