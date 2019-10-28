package cn.lixingyu.springmybatisthymeleaf.realm;

import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.exception.LoginCountException;
import cn.lixingyu.springmybatisthymeleaf.exception.UnActiveException;
import cn.lixingyu.springmybatisthymeleaf.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 18:38
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
//    @Lazy
    private UserServiceImpl userService;

    @Autowired
    private RedisTemplate myRedisTemplate;

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
        if (user.equals(null)) {
            throw new UnknownAccountException("用户不存在！");
        }
        if (user.getStatus() == 0) {
            throw new UnActiveException("用户未激活！");
        }

        //限制用户在2分钟内只能登录5次
        SimpleAuthenticationInfo custom = null;
        List usernameKey = null;
        //从Redis中获取以用户名为key的对象
        usernameKey = (List) myRedisTemplate.opsForList().range(user.getUsername(), 0, -1);
        //如果为0，表示还没有登录过
        if (usernameKey.size() == 0) {
            //向Redis存储以用户名为key，系统毫秒数为value的键值对
            myRedisTemplate.opsForList().leftPush(user.getUsername(), System.currentTimeMillis());
        } else if (usernameKey.size() >= 5) {
            //判断之前登录的时间是否在2分钟范围内
            for (int i = usernameKey.size()-1; i >= 0; --i) {
                if ((Long.parseLong(String.valueOf(System.currentTimeMillis()))) - Long.parseLong(usernameKey.get(i) + "") > 120000) {
                    myRedisTemplate.opsForList().rightPop(user.getUsername());
                }
            }
            //取出登录的list中最新的毫秒值，判断是否在两分钟内登录
            if ((Long.parseLong(String.valueOf(System.currentTimeMillis()))) - Long.parseLong(usernameKey.get(0) + "") > 120000) {
                //移除list中毫秒值最小的
                myRedisTemplate.opsForList().rightPop(user.getUsername());
                //Redis存储以用户名为key，系统毫秒数为value的键值对
                myRedisTemplate.opsForList().leftPush(user.getUsername(), System.currentTimeMillis());
            } else if (myRedisTemplate.opsForList().range(user.getUsername(), 0, -1).size() == 5) {
                throw new LoginCountException("用户2分钟内登录次数已达到5次！请2分钟后重试！");
            }
        }
        //登录成功
        custom = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "custom");
        //设置用户加密的盐值，这里使用的是用户名
        custom.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
        return custom;
    }
}
