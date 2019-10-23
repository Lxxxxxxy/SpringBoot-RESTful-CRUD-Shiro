package cn.lixingyu.springmybatisthymeleaf.realm;

import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.exception.UnActiveException;
import cn.lixingyu.springmybatisthymeleaf.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 18:38
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
//    @Lazy
    private UserServiceImpl userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> permissions = userService.getPermissions(username);
        Set<String> roles = userService.getRoles(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.login(username);
        if(user == null){
            throw new UnknownAccountException("用户不存在！");
        }
        if(user.getStatus() == 0){
            throw new UnActiveException("用户未激活！");
        }
        SimpleAuthenticationInfo custom = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "custom");
        //设置用户加密的盐值，这里使用的是用户名
        custom.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
        return custom;
    }
}
