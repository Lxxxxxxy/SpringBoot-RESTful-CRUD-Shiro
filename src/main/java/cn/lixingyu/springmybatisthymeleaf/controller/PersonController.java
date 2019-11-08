package cn.lixingyu.springmybatisthymeleaf.controller;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.service.impl.PersonServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:30
 */
@Controller
@Transactional
public class PersonController {

    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private RedisTemplate myRedisTemplate;

    @GetMapping(value = "/{path}")
    public String mapping(@PathVariable String path){
        return path;
    }

    @RequiresPermissions(value = {"admin:add","user:add"},logical= Logical.OR)
    @GetMapping(value = "/init")
    public String init() {
        Person p1 = new Person("lxy", 20,"重庆");
        Person p2 = new Person("xlr", 19,"重庆");
        Person p3 = new Person("yyy", 21,"重庆");
        personService.addPerson(p1);
        personService.addPerson(p2);
        personService.addPerson(p3);
        return "redirect:list";
    }

//    @RequiresRoles(value = {"user","admin","super","guest"},logical = Logical.OR)
//    @RequiresPermissions(value = {"admin:select","user:select"},logical= Logical.OR)
    @GetMapping(value = "/list")
    public String list(Model model) {
        //获取当前登录用户的信息
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        //取出最后一次登录时间
        List loginLastTime = myRedisTemplate.opsForList().range(username, 0, -1);
        //取出登录次数
        Integer loginCount = (Integer) myRedisTemplate.opsForValue().get(username + ":Login");
        //获取点赞排行剩余时间
        Long likeLastTime = myRedisTemplate.getExpire("personZset");
        if(loginLastTime.size()<=1){
            model.addAttribute("loginLastTime",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(loginLastTime.get(0)+""))));
        }else{
            model.addAttribute("loginLastTime",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(loginLastTime.get(1)+""))));
        }
        //获取用户列表
        Set<ZSetOperations.TypedTuple<Tuple>> allPerson = personService.getAllPerson();
        model.addAttribute("allPerson", allPerson);
        //登录次数
        model.addAttribute("loginCount", loginCount);
        //最后一次登录时间
        model.addAttribute("likeLastTime", likeLastTime);
        model.addAttribute("loginError", "用户名或密码错误！");
        return "list";
    }

    @RequiresPermissions("admin:delete")
    @DeleteMapping(value = "/person/{id}")
    public String delete(@PathVariable Integer id) {
        personService.deletePerson(id);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:edit")
    @PutMapping(value = "/person")
    public String editPerson(Person person){
        System.out.println(person);
        personService.editPerson(person);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:add")
    @PostMapping(value = "/person")
    public String addPerson(Person person){
        personService.addPerson(person);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:edit")
    @GetMapping(value = "/person/{id}")
    public String edit(@PathVariable Integer id , Model model){
        Person person = personService.getPerson(id);
        model.addAttribute("person",person);
        return "edit";
    }

    @PostMapping(value = "/person/like")
    @ResponseBody
    public String like(@RequestParam String id){
        return personService.like(Integer.parseInt(id));
    }

}
