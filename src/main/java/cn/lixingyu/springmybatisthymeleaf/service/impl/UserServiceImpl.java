package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.dao.UserDao;
import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 16:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username) {
        return userDao.login(username);
    }

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public Set<String> getPermissions(String username) {
        return userDao.getPermissions(username);
    }

    @Override
    public Set<String> getRoles(String username) {
        return userDao.getRoles(username);
    }

    @Override
    public void insertRole(String username, String role) {
        userDao.insertRole(username,role);
    }

    @Override
    public void insertPermissions(String username, String role) {
        if(role.equals("admin")){
            userDao.insertPermissions(username,"admin:*");
        }else{
            userDao.insertPermissions(username,"user:*");
        }
    }
}
