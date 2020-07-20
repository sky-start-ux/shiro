package com.example.shirospringboot.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager webSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        //添加shiro的内置过滤器
        /**
         * anon：无需认证就可以访问
         * authc：必须认知才可以访问
         * user：必须拥有 记住我 功能才可以访问
         * perms：拥有对某个资源的权限才能访问
         * role：拥有某个角色才可以访问
         */
        //拦截
        Map<String,String> filterMap = new LinkedHashMap<>();
        //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/toRegister","anon");
        filterMap.put("/register","anon");
        filterMap.put("/user/*","authc");
        //filterMap.put("/user/update","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        return shiroFilterFactoryBean;
    }

    //DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager webSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    //创建Realm对象，需要自定义类
    @Bean
    public UserRealm userRealm(){

        /*return new UserRealm();*/
        UserRealm userRealm = new UserRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);

        userRealm.setCredentialsMatcher(credentialsMatcher);

        return userRealm;

    }
}
