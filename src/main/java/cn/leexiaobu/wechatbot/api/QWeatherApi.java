package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.leexiaobu.wechatbot.api.entiy.QAir;
import cn.leexiaobu.wechatbot.api.entiy.QWeather;
import cn.leexiaobu.wechatbot.api.entiy.TianQiEntity;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.util.Strings;

public class QWeatherApi extends CommonApi {
    String locationString = MyEnvironmentUtil.getApiString("qweather.location");
    String url = MyEnvironmentUtil.getApiString("qweather.url");
    String token = MyEnvironmentUtil.getApiString("qweather.key");


    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        String[] split = locationString.split("#");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < split.length; i++) {
            String locationInfo = split[i];
            String[] location = locationInfo.split(",");
            String weatherUrl = String.format(url, "weather", location[1], token);
            String weatherResult = HttpUtil.get(weatherUrl);
            Object ob = JSONUtil.getByPath(JSONUtil.parse(weatherResult), "now");
            QWeather qWeather = JSONObject.parseObject(ob.toString(), QWeather.class);
            String airUrl = String.format(url, "air", location[1], token);
            String airResult = HttpUtil.get(airUrl);
            Object object = JSONUtil.getByPath(JSONUtil.parse(airResult), "now");
            QAir qAir = JSONObject.parseObject(object.toString(), QAir.class);
            sb.append("【" + location[0] + "】" + qWeather + qAir + "\n");
            if (count++ > 1) {
                client.sendTxtMsg(sb.toString(), wxid);
                count=0;
                sb = new StringBuilder();
            }
        }if(!Strings.isEmpty(sb.toString())){
            client.sendTxtMsg(sb.toString(), wxid);
        }
    }
}
