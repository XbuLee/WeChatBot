package cn.leexiaobu.wechatbot.api;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leexiaobu
 * @date 2022-07-07 23:00
 */
public class ShaDiaoTuApi extends CommonApi {

  //  String url = MyEnvironmentUtil.getApiString("shadiao.url");
  String url = "http://jandan.net/";
  TimedCache<String, String> timedCache = CacheUtil.newTimedCache(3600 * 10);
  long lastUpdateTime = 0;
  TreeMap<String, Integer> treeMap = new TreeMap<>();
  HashMap<String, Integer> hashMap = new HashMap<>();
  Random random = new Random(100);

  {
    hashMap.put("top-3days", 25);
    hashMap.put("top-tucao", 30);
    hashMap.put("top-7days", 17);
    hashMap.put("top-4h", 64);
    hashMap.put("top", 50);
    hashMap.put("top-ooxx", 57);
  }

  @Override
  public WechatMsg getWeChatMsg(String content, String wxid) {
    long now = System.currentTimeMillis();
    System.out.println(now - lastUpdateTime);
    if (timedCache.isEmpty() || now - lastUpdateTime > 3600000) {
      treeMap.clear();
      Pattern r = Pattern.compile("//tva[0-9].sinaimg.cn/large.*?(?=\")");
      Set<Entry<String, Integer>> entries = hashMap.entrySet();
      for (Entry<String, Integer> entry : entries) {
        String key = entry.getKey();
        Integer value = entry.getValue();
        String realUrl = url + key;
        String webHtml = HttpUtil.get(realUrl);
        Matcher m = r.matcher(webHtml);
        while (m.find()) {
          String picUrl = webHtml.substring(m.start(), m.end());
          treeMap.put(picUrl, value * random.nextInt());
          timedCache.put(picUrl, picUrl);
        }
      }
    }
    String url = "http:" + timedCache.get(treeMap.pollFirstEntry().getKey());
    String suffix = FileUtil.getSuffix(url);
    String fileName = now + "." + suffix;
    File file = new File(fileName);
    HttpUtil.downloadFile(url, file);
    return new WechatMsg(file.getAbsolutePath(), wxid, MsgType.SEND_PIC.TYPE());
  }

  public static void main(String[] args) {
    ShaDiaoTuApi shaDiaoTuApi = new ShaDiaoTuApi();
    shaDiaoTuApi.getWeChatMsg("124", "2512");
  }
}