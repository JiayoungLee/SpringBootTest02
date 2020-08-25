package com.hqyj.javaSpringBoot.config.shiro;

import com.hqyj.javaSpringBoot.modules.account.entity.Resource;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.account.service.ResourceService;
import com.hqyj.javaSpringBoot.modules.account.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/25 0025 9:09
 * version 1.0
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                List<Resource> resourcesTmp = resourceService.getResourcesByRoleId(role.getRoleId());
                if (resourcesTmp != null && !resourcesTmp.isEmpty()) {
                    resourcesTmp.stream().forEach(resource -> {
                        simpleAuthorizationInfo.addStringPermission(resource.getPermission());
                    });
                }

            });
        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException("The account do not exist");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
