package cn.lixingyu.springmybatisthymeleaf.dao;

import cn.lixingyu.springmybatisthymeleaf.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 16:39
 */
@Component
public interface UserDao {
    @Select("select * from user where username=#{username}")
    User login(String username);

    @Insert("insert into user values(#{id},#{username},#{password},#{mailAddress},#{status})")
    void register(User user);

    @Select("select permission from permissions where username=#{username}")
    Set<String> getPermissions(String username);

    @Select("select role from roles where username=#{username}")
    Set<String> getRoles(String username);

    @Insert("insert into role values(#{username},#{role})")
    void insertRole(String username,String role);

    @Insert("insert into permissions value(#{username},#{permission})")
    void insertPermissions(String username,String permission);

    @Update("update user set status = 1 where id = #{id}")
    void changeUserStatus(String id);
}
