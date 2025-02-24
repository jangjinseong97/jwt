package com.green.jwt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "JWT",
                description = "JWT 연습",
                version = "v0.1"
        )
        , security = @SecurityRequirement(name = "Authorization")
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
                name = "Authorization",
                in = SecuritySchemeIn.HEADER,
                bearerFormat = "JWT",
                scheme = "Bearer"
)

public class SwaggerConfiguration {
}
