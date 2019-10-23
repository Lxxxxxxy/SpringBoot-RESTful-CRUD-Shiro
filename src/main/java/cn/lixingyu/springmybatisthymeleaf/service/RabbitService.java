package cn.lixingyu.springmybatisthymeleaf.service;

import cn.lixingyu.springmybatisthymeleaf.entity.User;

/**
 * @author Rlxy93
 * @time 2019/10/23 08:41
 */
public interface RabbitService {

    public void sendMail(User user);

}
