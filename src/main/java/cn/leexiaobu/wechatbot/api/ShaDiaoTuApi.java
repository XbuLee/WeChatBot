package cn.leexiaobu.wechatbot.api;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leexiaobu
 * @date 2022-07-07 23:00
 */
public class ShaDiaoTuApi extends CommonApi {

    String url = MyEnvironmentUtil.getApiString("shadiao.url");
    long lastUpdateTime = 0;
    HashMap<String, Integer> hashMap = new HashMap<>();
    Random random = new Random();
    CopyOnWriteArrayList<PicInfo> picInfos = new CopyOnWriteArrayList<>();

    {
        hashMap.put("top-3days", 25);
        hashMap.put("top-tucao", 30);
        hashMap.put("top-7days", 17);
        hashMap.put("top-4h", 64);
        hashMap.put("top", 50);
//        hashMap.put("top-ooxx", 57);
    }

    public static void main(String[] args) {
        ShaDiaoTuApi shaDiaoTuApi = new ShaDiaoTuApi();
        System.out.println(shaDiaoTuApi.random.nextInt(100));
        System.out.println(shaDiaoTuApi.random.nextInt(100));
        System.out.println(shaDiaoTuApi.random.nextInt(100));
        System.out.println(shaDiaoTuApi.random.nextInt(100));

    }

    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        long now = System.currentTimeMillis();
        if (picInfos.isEmpty() || now - lastUpdateTime > 3600000) {
            lastUpdateTime = now;
            picInfos.clear();
            Pattern r = Pattern.compile("//tva[0-9].sinaimg.cn/large.*?jpg(?=\")");
            Set<Entry<String, Integer>> entries = hashMap.entrySet();
            for (Entry<String, Integer> entry : entries) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                String realUrl = url + key;
                String webHtml = HttpUtil.get(realUrl);
                Matcher m = r.matcher(webHtml);
                while (m.find()) {
                    String picUrl = webHtml.substring(m.start(), m.end());
                    picInfos.add(new PicInfo(picUrl, value * random.nextInt(100)));
                }
            }
            Collections.sort(picInfos);
        }
        //并发异常
        PicInfo remove = picInfos.remove(picInfos.size() - 1);
        String url = "http:" + remove.getUrl();
        String suffix = FileUtil.getSuffix(url);
        String fileName = now + "." + suffix;
        File file = new File(fileName);
        HttpUtil.downloadFile(url, file);
        client.sendPicMsg(file.getAbsolutePath(), wxid);
    }
}

class PicInfo implements Comparable<PicInfo> {
    String url;
    int record;

    @Override
    public int compareTo(PicInfo obj) {
        return record - obj.record;
    }

    public PicInfo(String url, int record) {
        this.url = url;
        this.record = record;
    }

    public String getUrl() {
        return url;
    }

    public int getRecord() {
        return record;
    }
}