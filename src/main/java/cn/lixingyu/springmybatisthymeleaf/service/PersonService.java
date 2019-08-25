package cn.lixingyu.springmybatisthymeleaf.service;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.entity.User;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:33
 */
public interface PersonService {

    Boolean addPerson(Person person);
    List<Person> getAllPerson();
    Boolean deletePerson(Integer id);
    Person getPerson(Integer id);
    Boolean editPerson(Person person);
}
