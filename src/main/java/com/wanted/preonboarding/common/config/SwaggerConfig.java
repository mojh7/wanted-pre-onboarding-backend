package com.wanted.preonboarding.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("채용공고 서비스 API")
                .description("본 서비스는 기업의 채용을 위한 웹 서비스 입니다.</br>" +
                        "회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.")
                .version("1.0.0");
    }
}
