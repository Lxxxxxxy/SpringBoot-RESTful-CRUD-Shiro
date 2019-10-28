package cn.lixingyu.springmybatisthymeleaf.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Rlxy93
 * @time 2019/10/27 11:52
 */
public class LoginCountException extends AuthenticationException {
    public LoginCountException(String msg){
        super(msg);
    }
}
