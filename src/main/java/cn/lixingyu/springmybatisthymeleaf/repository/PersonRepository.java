package cn.lixingyu.springmybatisthymeleaf.repository;

import cn.lixingyu.springmybatisthymeleaf.entity.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:35
 */
@Repository
@Mapper
public interface PersonRepository {

//    @Insert("insert into person values(null,#{name},#{age},#{address})")
//    void addPerson(Person person);

    @Insert("insert into person values(null,#{name},#{age},#{address})")
    void addPerson(Person person);

    @Select("select * from person")
    List<Person> getAllPerson();

    @Delete("delete from person where id = #{id}")
    Boolean deletePerson(Integer id);

    @Select("select * from person where id = #{id}")
    Person getPerson(Integer id);

    @Update("update person set name = #{name},age = #{age},address = #{address} where id = #{id}")
    void editPerson(Person person);
}
