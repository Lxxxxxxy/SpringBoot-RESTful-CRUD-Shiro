package cn.lixingyu.springmybatisthymeleaf.controller;

import cn.lixingyu.springmybatisthymeleaf.service.impl.PersonServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 11:42
 */
@Controller
public class MappingController {

    @Autowired
    private PersonServiceImpl personService;

    @RequestMapping(value = "/")
    public String toList(){
        return "redirect:/list";
    }

    @GetMapping(value = "/login")
    public String skipLogin(){
        Subject subject = SecurityUtils.getSubject();
        //设置自动登录后设置不能访问login页（判断是否登录，如果登录了，访问login页时就直接跳转到list页面）
        if(subject.isAuthenticated()){
            return "redirect:/list";
        }else{
            return "login";
        }
    }

    @GetMapping(value = "/register")
    public String registerPage(){
        return "register";
    }

}
