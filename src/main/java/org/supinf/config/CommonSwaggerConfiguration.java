package org.supinf.config;

import com.fasterxml.classmate.TypeResolver;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author BLU Kwensy Eli
 */
@Configuration
@EnableSwagger2
public class CommonSwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.supinf"))
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class, CompletableFuture.class)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class))
                )
                .useDefaultResponseMessages(false);
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Supfile REST API")
                .description("Simple REST API to store, manage and share your files in an online storage.")
                .version("1.0")
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        String[] supportedMethods = {};
        return new UiConfiguration( null);
    }
}
