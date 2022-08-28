package cn.leexiaobu.wechatbot.api.entiy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QAir {

    //    private Date pubTime;
    private String aqi;
    private String level;
    private String category;
    private String primary;
    private String pm10;
    private String pm2p5;
    private String no2;
    private String so2;
    private String co;
    private String o3;

    @Override
    public String toString() {
        if ("1".equals(level)) {
            return String.format("空气质量%s.",
                    aqi);
        }
        return String.format("空气质量%s,%s级,%s,主要污染物为%s,pm2.5 %sμg/m3。",
                aqi, level, category, primary, pm2p5).replace("为NA", "无");
    }
}