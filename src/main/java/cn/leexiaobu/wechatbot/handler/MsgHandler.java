package cn.leexiaobu.wechatbot.handler;

import cn.leexiaobu.wechatbot.client.WechatBotClient;

/**
 * @date 2022-07-04
 */
public interface MsgHandler {

  void onMessage(WechatBotClient wechatBotClient,String s);
}