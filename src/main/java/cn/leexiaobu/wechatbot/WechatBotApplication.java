package cn.leexiaobu.wechatbot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("cn.leexiaobu.wechatbot.mapper")
public class WechatBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatBotApplication.class, args);
    }

}
