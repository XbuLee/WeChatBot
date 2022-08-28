package cn.leexiaobu.wechatbot.api;

import cn.hutool.http.HttpUtil;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageApi extends CommonApi {
    String url = MyEnvironmentUtil.getApiString("image.url");

    @Override
    public void handleMsg(String content, String wxid, WechatBotClient client) {
        String fileName = System.currentTimeMillis() + ".jpg";
        String redirectUrl = null;
        try {
            redirectUrl = getRedirectUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(fileName);
        String prefix = url.substring(0, url.lastIndexOf("/") + 1);
        HttpUtil.downloadFile(prefix + redirectUrl, file);
        String absolutePath = file.getAbsolutePath();
        client.sendPicMsg(absolutePath, wxid);
    }

    private String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }
}
