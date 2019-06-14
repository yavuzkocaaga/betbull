package com.betbull.social.football.config;


import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.app-info.name}")
    private String name;

    @Value("${swagger.app-info.description}")
    private String description;

    @Value("${swagger.app-info.version}")
    private String version;

    @Value("${swagger.app-info.licenseUrl}")
    private String licenseUrl;

    @Value("${swagger.app-info.license}")
    private String license;

    @Value("${swagger.app-info.contact.name}")
    private String contactName;

    @Value("${swagger.app-info.contact.url}")
    private String contactURL;

    @Value("${swagger.app-info.contact.email}")
    private String contactEmail;

    @Value("${swagger.app-info.termofserviceurl}")
    private String termOfServiceUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .deepLinking(true)
                .docExpansion(DocExpansion.NONE)
                .showExtensions(true)
                .defaultModelExpandDepth(1)
                .defaultModelsExpandDepth(1)
                .operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

    private ApiInfo apiInfo() {
        final Contact contact = new Contact(contactName, contactURL, contactEmail);
        return new ApiInfo(name, description, version, termOfServiceUrl, contact, license, licenseUrl, Collections.emptyList());
    }
}
