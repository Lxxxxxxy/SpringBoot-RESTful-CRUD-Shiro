package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.dao.UserDao;
import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.exception.ChangeStatusException;
import cn.lixingyu.springmybatisthymeleaf.service.UserService;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(Log.class);

    @Override
    public User login(String username) {
        return userDao.login(username);
    }

    @Override
    public void register(User user) {
        userDao.register(user);
        rabbitTemplate.convertAndSend("springmybatisthymeleaf.direct","mail",user);
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

    @Override
    public void changeUserStatus(String id) {
        if(id.equals("")){
            throw new ChangeStatusException("激活用户失败！");
        }
        try{
            userDao.changeUserStatus(id);
            logger.info("激活"+id+"成功！");
        }catch (Exception e){
            throw new ChangeStatusException("激活用户失败！");
        }
    }
}
