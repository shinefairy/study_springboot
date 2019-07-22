package com.wsy.mybatis_plus_demo.realm;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.wsy.mybatis_plus_demo.entity.AdminUser;
import com.wsy.mybatis_plus_demo.entity.User;
import com.wsy.mybatis_plus_demo.service.AdminUserService;
import com.wsy.mybatis_plus_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private AdminUserService adminUserService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     *  认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("用户登录认证:用户名:{},明文密码:{}",token.getUsername(),token.getPassword());
        //调用service通过用户名查找用户
        String username = token.getUsername();
        AdminUser adminUser = adminUserService.lambdaQuery().eq(AdminUser::getSysUserName, token.getUsername()).list().get(0);
        if(ObjectUtils.isNotEmpty(adminUser)){
            //返回密码
            return new SimpleAuthenticationInfo(adminUser,adminUser.getSysUserPwd(), ByteSource.Util.bytes(username), getName());
        }else{
            //用户不存在
            return null;
        }

    }
}
