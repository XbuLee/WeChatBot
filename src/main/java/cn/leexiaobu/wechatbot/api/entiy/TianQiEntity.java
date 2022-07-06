package cn.leexiaobu.wechatbot.api.entiy;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TianQiEntity {
    private List<Results> results;

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }

    public String getResult() {
        Location location = results.get(0).getLocation();
        Now now = results.get(0).getNow();
        return location.getName() + ":" + now.getText() + "," + now.getTemperature() + "C°";
    }
}

@Data
class Location {

    private String id;
    private String name;
    private String country;
    private String path;
    private String timezone;
    private String timezone_offset;
}

@Data
class Results {

    private Location location;
    private Now now;
    private Date last_update;
}

@Data
class Now {

    private String text;
    private String code;
    private String temperature;
}