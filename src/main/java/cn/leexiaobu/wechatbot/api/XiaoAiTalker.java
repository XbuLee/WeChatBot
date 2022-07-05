package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;

/**
 * @date 2022-07-05
 */
public class XiaoAiTalker implements CommonApi {

  String url = "http://81.70.100.130/api/xiaoai.php?msg=%s&n=text";

  public static void main(String[] args) {
    XiaoAiTalker xiaoAiTalker = new XiaoAiTalker();
    System.out.println(xiaoAiTalker.getTxtResponse("124"));
  }

  @Override
  public String getTxtResponse(String content) {
    String result = HttpUtil.get(String.format(url, content));
    return result;
  }
}