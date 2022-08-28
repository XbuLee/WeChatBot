package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;

public class XiaoBinApi extends CommonApi {
    String xb_wxid = MyEnvironmentUtil.getString("wechat.xb_wxid");
    private String lastChatId;

    public void handMsgFromXb(String content, String wxid, WechatBotClient client) {
        client.sendTxtMsg(content, lastChatId);
    }

    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        lastChatId = wxid;
        client.sendTxtMsg(content, xb_wxid);
    }
}
