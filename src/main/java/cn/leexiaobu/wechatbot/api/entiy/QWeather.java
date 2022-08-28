package cn.leexiaobu.wechatbot.api.entiy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QWeather {
    private String temp;
    private String feelsLike;
    private String icon;
    private String text;
    private String wind360;
    private String windDir;
    private String windScale;
    private String windSpeed;
    private String humidity;
    private String precip;
    private String pressure;
    private String vis;
    private String cloud;
    private String dew;

    @Override
    public String toString() {
        return ":" + "当前" + temp + "度,体感" + feelsLike + "度," + text + ",风力"
                + windScale + "级" + windSpeed + "km/h,湿度" + humidity + "%,气压" + pressure + "百帕,可见度" + vis + "公里。";
    }
}
