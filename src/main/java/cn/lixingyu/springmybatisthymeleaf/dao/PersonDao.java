package cn.lixingyu.springmybatisthymeleaf.dao;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:35
 */
@Component
public interface PersonDao {

    @Insert("insert into person values(null,#{name},#{age},#{address})")
    Boolean addPerson(Person person);

    @Select("select * from person")
    List<Person> getAllPerson();

    @Delete("delete from person where id = #{id}")
    Boolean deletePerson(Integer id);

    @Select("select * from person where id = #{id}")
    Person getPerson(Integer id);

    @Insert("update person set name = #{name},age = #{age},address = #{address} where id = #{id}")
    Boolean editPerson(Person person);

}
