package com.learn.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
@PropertySource(value = {"classpath:swagger_dev.properties"}, ignoreResourceNotFound = true, encoding = "utf-8")
public class SwaggerApiConfiguration {

    @Value("${swagger.host}")
    String host;

    @Value("${swagger.base.package}")
    String basePackage;

    @Value("${swagger.termsOfServiceUrl}")
    String termsOfServiceUrl;

    @Value("${swagger.title}")
    String title;

    @Value("${swagger.is.show}")
    boolean isShow;

    @Value("${swagger.author}")
    String author;

    @Value("${swagger.url}")
    String url;

    @Value("${swagger.email}")
    String email;

    @Value("${swagger.version}")
    String version;

    @Value("${swagger.description}")
    String description;

    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .host(host)
                .enable(isShow)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(title)
                //创建人及相关信息
//                .contact(new Contact(author, url, email))
                //版本号
                .version(version)
                .termsOfServiceUrl(termsOfServiceUrl)
                //描述
                .description(description)
                .build();
    }


}
