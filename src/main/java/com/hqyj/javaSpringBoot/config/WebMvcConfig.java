package com.hqyj.javaSpringBoot.config;

import com.hqyj.javaSpringBoot.filter.RequestParamaFilter;
import com.hqyj.javaSpringBoot.interceptor.RequestViewInterceptor;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author  Jayoung
 * createDate  2020/8/11 0011 10:28
 * version 1.0
 */
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${server.http.port}")
    private int httpPort;

//    @Value("${spring.resource.path}")
//    private String relativePath;
//
//    @Value("${spring.resource.path.pattern}")
//    private String relativePathPattern;
//
//    @Value("${spring.resource.folder.windows}")
//    private String locationPathForWindows;
//
//    @Value("${spring.resource.folder.linux}")
//    private String locationPathForLinux;

    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Autowired
    private RequestViewInterceptor requestViewInterceptor;

    @Bean
    public Connector connector() {
        Connector connector = new Connector();
        connector.setPort(httpPort);
        connector.setScheme("http");
        return connector;
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector());
        return tomcat;
    }

    @Bean
    public FilterRegistrationBean<RequestParamaFilter> register() {
        FilterRegistrationBean<RequestParamaFilter> register =
                new FilterRegistrationBean<RequestParamaFilter>();
        register.setFilter(new RequestParamaFilter());
        return register;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestViewInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("win")) {
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX + resourceConfigBean.getLocationPathForWindows());
        } else {
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX + resourceConfigBean.getLocationPathForLinux());
        }
    }

    //欢迎页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("welcome");
    }
}
