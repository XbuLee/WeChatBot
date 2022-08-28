package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.client.WechatBotClient;

public abstract class CommonApi {
    public abstract void handleMsg(String content, String wxid, WechatBotClient client);
}
