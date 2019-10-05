package cn.lixingyu.springmybatisthymeleaf.controller;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.service.impl.PersonServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:30
 */
@Controller
@Transactional
public class PersonController {

    @Autowired
    private PersonServiceImpl personService;

    @GetMapping(value = "/{path}")
    public String mapping(@PathVariable String path){
        return path;
    }

    @RequiresPermissions(value = {"admin:add","user:add"},logical= Logical.OR)
    @GetMapping(value = "/init")
    public String init() {
        Person p1 = new Person("lxy", 20, "重庆");
        Person p2 = new Person("xlr", 19, "重庆");
        Person p3 = new Person("yyy", 21, "重庆");
        personService.addPerson(p1);
        personService.addPerson(p2);
        personService.addPerson(p3);
        return "redirect:list";
    }

//    @RequiresRoles(value = {"user","admin","super","guest"},logical = Logical.OR)
//    @RequiresPermissions(value = {"admin:select","user:select"},logical= Logical.OR)
    @GetMapping(value = "/list")
    public String list(Model model) {
        List<Person> allPerson = personService.getAllPerson();
        model.addAttribute("allPerson", allPerson);
        return "list";
    }

    @RequiresPermissions("admin:delete")
    @DeleteMapping(value = "/person/{id}")
    public String delete(@PathVariable Integer id) {
        Boolean isDelete = personService.deletePerson(id);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:edit")
    @PutMapping(value = "/person")
    public String editPerson(Person person){
        System.out.println(person);
        Boolean isEdit = personService.editPerson(person);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:add")
    @PostMapping(value = "/person")
    public String addPerson(Person person){
        Boolean isAdd = personService.addPerson(person);
        return "redirect:/list";
    }

    @RequiresPermissions("admin:edit")
    @GetMapping(value = "/person/{id}")
    public String edit(@PathVariable Integer id , Model model){
        Person person = personService.getPerson(id);
        model.addAttribute("person",person);
        return "edit";
    }

}
