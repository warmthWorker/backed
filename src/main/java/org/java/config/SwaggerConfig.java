//package org.java.config;
//
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
////@EnableSwagger2  // 老版本开启swagger
////@EnableOpenApi   // 网上搜到的解决报错添加的
//public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApi1() {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("我的接口文档")
//                .contact(new Contact("mySwagger", "heheheh", "hello@163.com"))
//                .version("1.0")
//                .description("接口文档描述")
//                .build();
//
//        //docket对象用于封装接口文档相关信息
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .groupName("用户接口组")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.xf.swagger.controller"))
//                .build();
//        return docket;
//    }
//}
