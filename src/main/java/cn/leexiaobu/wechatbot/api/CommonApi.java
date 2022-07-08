package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.domain.WechatMsg;

public abstract class CommonApi {
    public abstract WechatMsg getWeChatMsg(String content, String wxid);

    public void getWeChatMsg(String content, String wxid, WechatBotClient client) {
    }
}
