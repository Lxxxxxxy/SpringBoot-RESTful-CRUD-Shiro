package cn.lixingyu.springmybatisthymeleaf.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Rlxy93
 * @time 2019/10/23 09:51
 */
public class ChangeStatusException extends AuthenticationException {

    public ChangeStatusException(String msg){
        super(msg);
    }
}
