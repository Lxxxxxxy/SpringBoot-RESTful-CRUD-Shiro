package cn.lixingyu.springmybatisthymeleaf.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * @author lxxxxxxy
 * @time 2019/08/13 17:17
 */
//@Configuration
public class ErrorPageConfig {

//    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage = new ErrorPage(org.thymeleaf.exceptions.TemplateInputException.class,
                        "/error/errorPage");
                factory.addErrorPages(errorPage);
            }
        };
    }

}