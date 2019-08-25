package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.dao.PersonDao;
import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:34
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Boolean addPerson(Person person) {
        try{
            personDao.addPerson(person);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Person> getAllPerson() {
        List<Person> allPerson = personDao.getAllPerson();
        return allPerson;
    }

    @Override
    public Boolean deletePerson(Integer id) {
        Boolean isDelete = personDao.deletePerson(id);
        return isDelete;
    }

    @Override
    public Person getPerson(Integer id) {
        Person person = personDao.getPerson(id);
        return person;
    }

    @Override
    public Boolean editPerson(Person person) {
        Boolean isEdit = personDao.editPerson(person);
        return isEdit;
    }
}
