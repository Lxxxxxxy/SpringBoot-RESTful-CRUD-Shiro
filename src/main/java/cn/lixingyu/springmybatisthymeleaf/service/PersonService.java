package cn.lixingyu.springmybatisthymeleaf.service;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:33
 */
public interface PersonService {

    void addPerson(Person person);
    List<Person> getAllPerson();
    void deletePerson(Integer id);
    Person getPerson(Integer id);
    Person editPerson(Person person);
}
