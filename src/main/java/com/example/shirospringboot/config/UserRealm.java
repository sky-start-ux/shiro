package com.example.shirospringboot.config;

import com.example.shirospringboot.domain.User;
import com.example.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Subject：用户
 * SecurityManager：管理所有用户
 * Realm：连接数据
 * */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权==>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add");
        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(user.getRole());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证==>doGetAuthenticationInfo");
       /* //用户名，密码，数据库中取
        String username = "sky";
        String password = "123456";*/
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUser(token.getUsername());
        if (user == null)
            return null;
       /* if (!token.getUsername().equals(username))
            return null;//抛出异常，UnknownAccountException*/
        //return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
    }
}
