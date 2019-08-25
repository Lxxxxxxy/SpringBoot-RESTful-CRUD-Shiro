package cn.lixingyu.springmybatisthymeleaf.resolver;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lxxxxxxy
 * @time 2019/08/10 16:47
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UnauthorizedException) {
            ModelAndView mv = new ModelAndView("unauthorized");
            return mv;
        }
        return null;
    }
}