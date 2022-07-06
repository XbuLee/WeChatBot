package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import com.alibaba.fastjson.JSONObject;

public class QinghuaApi extends CommonApi {
    String url = MyEnvironmentUtil.getApiString("qinghua.url");
    String token = MyEnvironmentUtil.getApiString("qinghua.token");

    @Override
    public WechatMsg getWeChatMsg(String content, String wxid) {
        String tempResult = HttpUtil.get(String.format(url, token));
        String userName;
        String[] s = content.split(" ");
        if (s.length > 1) {
            userName = s[1];
        } else {
            userName = "大光头";
        }
        String result = JSONUtil.getByPath(JSONUtil.parse(tempResult), "data.content").toString();
        return new WechatMsg(userName + "," + result, wxid, MsgType.SEND_TXT.TYPE());
    }

}
