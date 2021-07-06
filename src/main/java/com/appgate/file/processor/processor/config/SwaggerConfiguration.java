package com.appgate.file.processor.processor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.appgate.file.processor.processor.api.controller" ) )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfo(
                "Processor file API",
                "API for processing geolocation plain file",
                "1.0",
                "https://www.appgate.com/",
                new Contact("MAS Global", "https://masglobalconsulting.com/", "info@masglobalconsulting.com"),
                "",
                "",
                Collections.emptyList()
        );
    }
}
