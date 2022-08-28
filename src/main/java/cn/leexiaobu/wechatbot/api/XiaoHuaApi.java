package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;

public class XiaoHuaApi extends CommonApi{
    String url = MyEnvironmentUtil.getApiString("xiaohua.url");

    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        String result = HttpUtil.get(String.format(url, content));
        client.sendTxtMsg(result,wxid);
    }
}
