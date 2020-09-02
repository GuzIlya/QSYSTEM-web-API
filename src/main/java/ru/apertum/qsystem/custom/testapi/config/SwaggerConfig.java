package ru.apertum.qsystem.custom.testapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.apertum.qsystem.custom.testapi"))
                .paths(PathSelectors.any())
                .build()
                .host("https://localhost:8080/")
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Test API",
                "Test API",
                "1.0",
                "Terms of Service",
                new Contact("TechPrimers", "https://www.youtube.com/TechPrimers",
                        "techprimerschannel@gmail.com").getName(),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0"
        );

        return apiInfo;
    }
}
