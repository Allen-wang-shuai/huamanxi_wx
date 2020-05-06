package club.wshuai.huamanxi_wx.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    //设置要扫描的包，设置后会扫描该包下所有带RestController注解的类
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("club.wshuai.huamanxi_wx"))
                .paths(PathSelectors.any())
                .build();
    }

    //设置API简介信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("花满蹊API文档")
                .description("花满蹊API文档")
//                .termsOfServiceUrl("/")
                .version("1.0")
                .build();
    }

}
