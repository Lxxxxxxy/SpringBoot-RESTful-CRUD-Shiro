package cn.lixingyu.springmybatisthymeleaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.lixingyu.springmybatisthymeleaf.dao")
public class SpringmybatisthymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmybatisthymeleafApplication.class, args);
    }

}
