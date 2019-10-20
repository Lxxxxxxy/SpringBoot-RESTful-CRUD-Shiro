package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.dao.PersonDao;
import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {@CacheEvict(cacheNames = "allPerson", key = "1", beforeInvocation = true)})
    public void addPerson(Person person) {
        personDao.addPerson(person);
    }

    @Override
    @Cacheable(cacheNames = "allPerson", key = "1")
    public List<Person> getAllPerson() {
        System.out.println("查询所有Person");
        List<Person> allPerson = personDao.getAllPerson();
        return allPerson;
    }

    @Override
    //beforeInvocation：设置在方法执行之前清除缓存
    @Caching(evict = {@CacheEvict(cacheNames = "person", key = "#id", beforeInvocation = true),
            @CacheEvict(cacheNames = "allPerson", key = "1")})
    public void deletePerson(Integer id) {
        personDao.deletePerson(id);
    }

    @Override
    @Cacheable(cacheNames = "person", key = "#id")
    public Person getPerson(Integer id) {
        Person person = personDao.getPerson(id);
        return person;
    }

    @Override
    @Caching(put = {@CachePut(cacheNames = "person", key = "#person.id")},
            evict = {@CacheEvict(cacheNames = "allPerson", key = "1", beforeInvocation = true)})
    public Person editPerson(Person person) {
        personDao.editPerson(person);
        return person;
    }
}
