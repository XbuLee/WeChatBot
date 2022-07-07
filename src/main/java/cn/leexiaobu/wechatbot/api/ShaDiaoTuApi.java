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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leexiaobu
 * @date 2022-07-07 23:00
 */
public class ShaDiaoTuApi extends CommonApi {

  String url = MyEnvironmentUtil.getApiString("shadiao.url");
  List<String> urlList;
  TreeSet<String> hashSet = new TreeSet<>();
  TimedCache<String, String> timedCache = CacheUtil.newTimedCache(3600);

  {
    urlList = Arrays.asList("top-3days", "top-7days", "top-tucao", "top-4h", "top",
        "top-ooxx");
  }

  @Override
  public WechatMsg getWeChatMsg(String content, String wxid) {
    if (timedCache.isEmpty()) {
      hashSet.clear();
      Pattern r = Pattern.compile("//tva[0-9].sinaimg.cn/large.*?(?=\")");
      for (String s : urlList) {
        String realUrl = url + s;
        String webHtml = HttpUtil.get(realUrl);
        Matcher m = r.matcher(webHtml);
        while (m.find()) {
          String substring = webHtml.substring(m.start(), m.end());
          timedCache.put(substring, substring);
          hashSet.add(substring);
        }
        System.out.println(hashSet.size());
      }
    }
    String url ="http:"+ hashSet.pollFirst();
    String suffix = FileUtil.getSuffix(url);
    String fileName = System.currentTimeMillis() + "." + suffix;
    File file = new File(fileName);
    HttpUtil.downloadFile(url, file);
    return new WechatMsg(file.getAbsolutePath(), wxid, MsgType.SEND_PIC.TYPE());
  }

  public static void main(String[] args) {

  }
}