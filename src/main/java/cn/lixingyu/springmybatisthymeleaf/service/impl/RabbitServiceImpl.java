package cn.lixingyu.springmybatisthymeleaf.service.impl;

import cn.lixingyu.springmybatisthymeleaf.config.MailConfig;
import cn.lixingyu.springmybatisthymeleaf.entity.User;
import cn.lixingyu.springmybatisthymeleaf.service.RabbitService;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rlxy93
 * @time 2019/10/23 08:41
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    Logger logger = LoggerFactory.getLogger(Log.class);

    @Autowired
    private MailConfig mailConfig;

    @Override
    @RabbitListener(queues = "springmybatisthymeleafQueue")
    public void sendMail(User user) {
        logger.info("开始给用户"+user.getUsername()+"发送邮件");
        try{
            mailConfig.sendSimpleMail(user.getemail(),"lixingyu.cn激活邮件","<a href=\"https://www.lixingyu.cn:8081/changeUserStatus?id="+user.getId()+"\">点我激活</a>",logger);
        }catch (Exception e){
            logger.error("发送邮件时发生异常！", e);
        }
    }
}
