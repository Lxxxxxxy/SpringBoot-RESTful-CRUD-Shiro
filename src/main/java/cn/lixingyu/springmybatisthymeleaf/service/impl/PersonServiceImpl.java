package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import cn.lixingyu.springmybatisthymeleaf.repository.PersonRepository;
import cn.lixingyu.springmybatisthymeleaf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:34
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RedisTemplate myRedisTemplate;

    @Override
//    @Caching(evict = {@CacheEvict(cacheNames = "allPerson", key = "1", beforeInvocation = true)})
    public void addPerson(Person person) {
        personRepository.addPerson(person);
        myRedisTemplate.opsForZSet().add("personZset", person, 0);
    }

    @Override
//    @Cacheable(cacheNames = "allPerson", key = <ZSetOperations.TypedTuple<Person>"1")
    public Set<ZSetOperations.TypedTuple<Tuple>> getAllPerson() {
        //从Redis查询是否有person列表
        Set<ZSetOperations.TypedTuple<Tuple>> personZset = (Set<ZSetOperations.TypedTuple<Tuple>>) myRedisTemplate.opsForZSet().reverseRangeWithScores("personZset", 0, -1);
        //不为空表示有，直接返回列表
        if (personZset.size() != 0) {
            return personZset;
        }
        List<Person> allPerson = personRepository.getAllPerson();
        for (Person p : allPerson) {
            //为空，给每个person赋值点赞数0
            p.setLikes(0l);
            //添加到Redis的Zset中
            myRedisTemplate.opsForZSet().add("personZset", p, 0);
            //设置超时时间为1分钟
            myRedisTemplate.expire("personZset",60000, TimeUnit.MILLISECONDS);
        }
        //因为保存进去是从小到大，反序按score获取person列表
        personZset = (Set<ZSetOperations.TypedTuple<Tuple>>) myRedisTemplate.opsForZSet().reverseRangeWithScores("personZset", 0, -1);
        if (personZset.size() != 0) {
            return personZset;
        }
        return personZset;
    }

    @Override
    //beforeInvocation：设置在方法执行之前清除缓存
//    @Caching(evict = {@CacheEvict(cacheNames = "person", key = "#id", beforeInvocation = true),
//            @CacheEvict(cacheNames = "allPerson", key = "1")})
    public void deletePerson(Integer id) {
        personRepository.deletePerson(id);
    }

    @Override
//    @Cacheable(cacheNames = "person", key = "#id")
    public Person getPerson(Integer id) {
        Person person = personRepository.getPerson(id);
        return person;
    }

    @Override
//    @Caching(put = {@CachePut(cacheNames = "person", key = "#person.id")},
//            evict = {@CacheEvict(cacheNames = "allPerson", key = "1", beforeInvocation = true)})
    public Person editPerson(Person person) {
        personRepository.editPerson(person);
        return person;
    }

    @Override
    public String like(Integer id) {
        Person person = getPerson(id);
        person.setLikes(0l);
//        person.setLikes(person.getLikes()+1);
//        myRedisTemplate.opsForZSet().add("personZset", person, person.getLikes());
        myRedisTemplate.opsForZSet().score("personZset", person);
        myRedisTemplate.opsForZSet().incrementScore("personZset", person, 1);
        return "success";
    }
}
