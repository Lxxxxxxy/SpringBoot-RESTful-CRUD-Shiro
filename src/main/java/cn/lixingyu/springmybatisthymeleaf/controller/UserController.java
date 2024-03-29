package cn.lixingyu.springmybatisthymeleaf.controller;

import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.exception.ChangeStatusException;
import cn.lixingyu.springmybatisthymeleaf.exception.LoginCountException;
import cn.lixingyu.springmybatisthymeleaf.exception.UnActiveException;
import cn.lixingyu.springmybatisthymeleaf.realm.CustomRealm;
import cn.lixingyu.springmybatisthymeleaf.service.impl.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 16:29
 */
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CustomRealm customRealm;

    @Autowired
    private RedisTemplate myRedisTemplate;

    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    Logger logger = LoggerFactory.getLogger(Log.class);

    @PostMapping(value = "/register")
    @ResponseBody
    public String register(User user, @RequestParam String role) {
        if (user == null) {
            return "<script>alert('注册失败！');window.location='/register'</script>";
        }
//        System.out.println(role);
        user.setId(UUID.randomUUID().toString());
        userService.insertPermissions(user.getUsername(), role);
//        System.out.println(user.toString());
        String md5 = new SimpleHash("md5", user.getPassword(), user.getUsername(), 5).toHex();
        user.setPassword(md5);
        user.setStatus(0);
        try {
            userService.register(user);
            return "<script>alert('注册成功！');window.location='/login'</script>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<script>alert('注册失败！');window.location='/register'</script>";
    }

    @PostMapping(value = "/login")
    public String login(User user, Model model, HttpServletRequest request) {
//        defaultSecurityManager.setRealm(customRealm);
//        SecurityUtils.setSecurityManager(defaultSecurityManager);
//        if(user == null){
//            return "/login";
//        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            token.setRememberMe(true);
            subject.login(token);
            logger.info(user.getUsername() + "    登录");
            //登录次数+1
            myRedisTemplate.opsForValue().increment(user.getUsername()+":Login");
            //登陆成功，向Redis存储以用户名为key，系统毫秒数为value的键值对
            myRedisTemplate.opsForList().leftPush(user.getUsername(), System.currentTimeMillis());
            return "redirect:/list";
        } catch (UnActiveException e) {
            model.addAttribute("loginError", e.getMessage());
        } catch (ChangeStatusException e) {
            e.printStackTrace();
            model.addAttribute("loginError", e.getMessage());
        } catch (LoginCountException e) {
            e.printStackTrace();
            model.addAttribute("loginError", e.getMessage());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("loginError", "用户名或密码错误！");
        }
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        subject.logout();
        logger.info(username + "    登出");
        return "redirect:/login";
    }

    @GetMapping(value = "/changeUserStatus")
    @ResponseBody
    public String changeUserStatus(@RequestParam String id){
        try {
            userService.changeUserStatus(id);
            return "<script>alert('激活成功！');window.location='/login'</script>";
        } catch (Exception e) {
        }
        return "<script>alert('激活失败！');window.location='/login'</script>";
    }

}
