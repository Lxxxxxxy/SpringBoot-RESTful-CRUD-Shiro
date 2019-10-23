package cn.lixingyu.springmybatisthymeleaf.service;

import cn.lixingyu.springmybatisthymeleaf.entity.User;

import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 16:37
 */
public interface UserService {
    User login(String username);

    void register(User user);

    Set<String> getPermissions(String username);

    Set<String> getRoles(String username);

    void insertRole(String username,String role);

    void insertPermissions(String username,String role);

    void changeUserStatus(String id);
}
