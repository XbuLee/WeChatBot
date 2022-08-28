package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.api.entiy.TianQiEntity;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import com.alibaba.fastjson.JSONObject;

public class TianQiApi extends CommonApi {
    String locationString = "beijing,chengdu,huhehaote,ningbo,xian";
    String url = MyEnvironmentUtil.getApiString("tianqi.url");
    String token = MyEnvironmentUtil.getApiString("tianqi.token");

    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        String[] split = locationString.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String realUrl = String.format(url, token, split[i]);
            String s = HttpUtil.get(realUrl);
            TianQiEntity tianQiEntity = JSONObject.parseObject(s, TianQiEntity.class);
            sb.append(tianQiEntity.getResult()).append("\n");
        }
        client.sendTxtMsg(sb.toString(),wxid);
    }
}
