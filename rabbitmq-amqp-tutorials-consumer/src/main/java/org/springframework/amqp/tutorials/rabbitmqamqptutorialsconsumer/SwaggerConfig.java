package org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer;

import com.fasterxml.classmate.TypeResolver;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private TypeResolver resolver;

    @Value("${swagger.version}")
    private String appVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .alternateTypeRules(getDefaultAlternateTypeRules())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.springframework"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Exposure Service Restitution REST API",
                "API that give eposure details",
                appVersion,
                null,
                new Contact("FTCareDis team", null, "PAR-BSC-DAT-SAFIR-CARE-DIS@socgen.com"),
                null,
                null,
                Collections.emptyList()
        );
    }

    private RecursiveAlternateTypeRule getDefaultAlternateTypeRules() {
        return new RecursiveAlternateTypeRule(resolver, List.of(
                new AlternateTypeRule(
                        resolver.resolve(Option.class, WildcardType.class),
                        resolver.resolve(java.util.Optional.class, WildcardType.class)),
                new AlternateTypeRule(
                        resolver.resolve(List.class, WildcardType.class),
                        resolver.resolve(java.util.List.class, WildcardType.class)),
                new AlternateTypeRule(
                        resolver.resolve(Set.class, WildcardType.class),
                        resolver.resolve(java.util.Set.class, WildcardType.class)),
                new AlternateTypeRule(
                        resolver.resolve(Map.class, WildcardType.class, WildcardType.class),
                        resolver.resolve(java.util.Map.class, WildcardType.class, WildcardType.class))
        ));
    }

}
