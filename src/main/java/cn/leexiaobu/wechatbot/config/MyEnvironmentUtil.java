package cn.leexiaobu.wechatbot.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MyEnvironmentUtil implements EnvironmentAware {
    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {

        env = environment;
    }

    public static String getString(String key) {
        return env.getProperty(key);
    }

    public static String getApiString(String key) {
        return env.getProperty("api." + key);
    }
}
