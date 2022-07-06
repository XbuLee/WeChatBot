package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.domain.WechatMsg;

public abstract class CommonApi {
    public abstract WechatMsg getWeChatMsg(String content, String wxid);
}
