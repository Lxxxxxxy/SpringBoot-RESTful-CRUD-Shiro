package cn.lixingyu.springmybatisthymeleaf.service;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import org.springframework.data.redis.core.ZSetOperations;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:33
 */
public interface PersonService {

    void addPerson(Person person);

    Set<ZSetOperations.TypedTuple<Tuple>> getAllPerson();

    void deletePerson(Integer id);

    Person getPerson(Integer id);

    Person editPerson(Person person);

    String like(Integer id);
}
