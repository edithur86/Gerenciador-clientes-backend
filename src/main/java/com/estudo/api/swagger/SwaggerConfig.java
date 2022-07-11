package com.estudo.api.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        List<Parameter> aParameters = getParameters();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.estudo.api"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .apiInfo(apiInfo());
    }

    private List<Parameter> getParameters() {
        ParameterBuilder authParameterBuilder = new ParameterBuilder();
        authParameterBuilder.name("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer xpto")
                .required(true)
                .build();
        List<Parameter> authParameters = new ArrayList<>();
        authParameters.add(authParameterBuilder.build());
        return authParameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Estudo Software Service: REST API")
                .description("Interface for estudo Software REST API.")
                .build();
    }

}
