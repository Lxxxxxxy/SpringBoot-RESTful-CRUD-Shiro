package cn.lixingyu.springmybatisthymeleaf.resolver;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lxxxxxxy
 * @time 2019/08/14 16:17
 */
@Component
public class errorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        return new ModelAndView("/error/errorPage",model);
    }
}
