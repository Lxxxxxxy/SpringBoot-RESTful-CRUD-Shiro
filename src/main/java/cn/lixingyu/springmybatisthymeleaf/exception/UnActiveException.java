package cn.lixingyu.springmybatisthymeleaf.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Rlxy93
 * @time 2019/10/23 09:32
 */
public class UnActiveException extends AuthenticationException {

    public UnActiveException(String msg){
        super(msg);
    }

}
