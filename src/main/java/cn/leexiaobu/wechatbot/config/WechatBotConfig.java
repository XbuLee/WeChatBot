package cn.leexiaobu.wechatbot.config;

import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.handler.MsgHandler;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatBotConfig {

  @Autowired
  MsgHandler msgHandler;
  /**
   * 微信bot 链接地址
   */
  @Value("${wechat.bot.url}")
  private String wechatBotUrl;

  /**
   * 初始化
   */
  @Bean
  public WechatBotClient initWechatBotClient() throws URISyntaxException {
    WechatBotClient botClient = new WechatBotClient(wechatBotUrl, msgHandler);
    // 建立连接
    botClient.connect();
    return botClient;
  }

}
