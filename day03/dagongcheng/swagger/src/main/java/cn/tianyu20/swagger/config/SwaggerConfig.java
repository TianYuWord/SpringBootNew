package cn.tianyu20.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value="true")
    private Boolean SwaggerEnabled;
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(SwaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.tianyu20.swagger"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("小宇玩转 Spring Boot")
                .termsOfServiceUrl("大致应该是一个邮箱地址")
                .version("1.0")
                .build();
    }
}
