package cn.boot.validatorpro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置Swagger 文档描述信息
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //文档是否开启的开关，变量
    @Value(value = "true")
    private boolean SwaggerEnabled;


    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(SwaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.boot.validatorpro"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger接口测试文档")
                .description("1.0版本")
                .termsOfServiceUrl("2949852842@qq.com")
                .version("1.0")
                .build();
    }


}