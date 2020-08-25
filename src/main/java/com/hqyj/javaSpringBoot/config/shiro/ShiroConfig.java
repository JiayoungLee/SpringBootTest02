package com.hqyj.javaSpringBoot.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author  Jayoung
 * createDate  2020/8/25 0025 14:01
 * version 1.0
 */
@Configuration
@Component
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    @Bean
    public DefaultSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        return manager;
    }


    /**
     * 配置shiro过滤器工厂
     * -----------------
     * 拦截权限
     * anon：匿名访问，无需登录
     * authc：登录后才能访问
     * user：登录过能访问
     * logout：登出
     * roles：角色过滤器
     * ------------------
     * URL匹配风格
     * ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/
     * *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1
     * **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     * -----------------------
     * 方法名不能乱写，如果我们定义为别的名称，又没有添加注册过滤器的配置，那么shiro会加载ShiroWebFilterConfiguration过滤器，
     * 该过滤器会寻找shiroFilterFactoryBean，找不到会抛出异常
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());

        shiroFilter.setLoginUrl("account/login");
        shiroFilter.setSuccessUrl("account/dashboard");

        Map<String,String> map = new LinkedHashMap<String, String>();
        map.put("/","anon");    //为了弄欢迎页
        map.put("/static/**","anon");
        map.put("/js/**", "anon");
        map.put("/css/**", "anon");
        map.put("/plugin/**", "anon");
        map.put("/account/login", "anon");
        map.put("/account/register", "anon");
        map.put("/api/login", "anon");
        map.put("/api/user", "anon");

        map.put("/common/**", "user");
        map.put("/test/**", "user");

        // 如果使用“记住我功能”，则采取user规则，如果必须要用户登陆，则采用authc规则
        map.put("/**","authc");

        return shiroFilter;
    }

    /*
    *  注册shiro方言，让Thymeleaf支持shiro标签
    * */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /*
    *  自动代理类，支持shiro的注解
    * */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator =
                new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /*
    * 开启shiro注解
    * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
