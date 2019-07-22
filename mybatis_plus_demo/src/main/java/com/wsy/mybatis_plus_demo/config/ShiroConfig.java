package com.wsy.mybatis_plus_demo.config;


import com.wsy.mybatis_plus_demo.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 凭证管理器
     （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码,更改密码生成规则和校验的逻辑一致即可;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数：比如散列两次 相当于md5(md5(""))
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);////storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        return hashedCredentialsMatcher;
    }

    /**
     * 添加用户自定义Realm，并传入加密规则传
     * @return
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        UserRealm userRealm =new UserRealm();
        //告诉realm,使用credentialsMatcher加密算法类来验证密文
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        userRealm.setCachingEnabled(false);//不进行缓存
        return userRealm;
    }

    /**
     * 安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager sessionManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * anon: 无需认证（登录）可以访问
     *      *       authc: 必须认证才可以访问
     *      *       user: 如果使用rememberMe的功能可以直接访问
     *      *       perms： 该资源必须得到资源权限才可以访问
     *      *       role: 该资源必须得到角色权限才可以访问
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        //必须配置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/tologin");
        //配置过滤器
        Map<String,String> filterMap =new LinkedHashMap<>();

        // 配置不会被拦截的链接 从上向下顺序判断
        filterMap.put("/css/*", "anon");
        filterMap.put("/js/*", "anon");
        filterMap.put("/js/*/*", "anon");
        filterMap.put("/js/*/*/*", "anon");
        filterMap.put("/images/*/**", "anon");
        filterMap.put("/layui/*", "anon");
        filterMap.put("/layui/*/**", "anon");

        filterMap.put("/user/login","anon");
        //允许图片验证码controller放行
        filterMap.put("/getKaptchaImage","anon");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterMap.put("/user/logout","logout");

        //其他资源均进行拦截
        filterMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

}
