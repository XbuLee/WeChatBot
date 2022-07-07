package cn.leexiaobu.wechatbot.api;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @date 2022-07-06
 */
public class ExchangeRate {

  ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
  public Iterator<ExchangeRateProvider> providers(boolean refresh) {
    if (refresh) {
      loader.reload();
    }
    return loader.iterator();
  }
}