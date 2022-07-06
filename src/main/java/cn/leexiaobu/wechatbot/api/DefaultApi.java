package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;

public class DefaultApi extends CommonApi {
     String url = MyEnvironmentUtil.getApiString("default.url");

    @Override
    public WechatMsg getWeChatMsg(String content, String wxid) {
        String result = HttpUtil.get(String.format(url, content));
        return new WechatMsg(result, wxid, MsgType.SEND_TXT.TYPE());
    }
}
