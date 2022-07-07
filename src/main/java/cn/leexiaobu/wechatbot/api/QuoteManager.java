package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import java.time.LocalDate;
import java.util.List;

/**
 * @date 2022-07-06
 */
public interface QuoteManager {

  String url = MyEnvironmentUtil.getApiString("tianqi.url");


  List<Quote> getQuotes(String baseCurrency, LocalDate date);
}
