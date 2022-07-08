package cn.leexiaobu.wechatbot.api;

import cn.leexiaobu.wechatbot.api.entiy.TelnetClientExample;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.*;

import java.io.*;

public class GameApi extends CommonApi {
    TelnetClientExample telnetClientDecorator;
    String host = MyEnvironmentUtil.getApiString("game.host");
    int port = Integer.parseInt(MyEnvironmentUtil.getApiString("game.port"));


    @Override
    public WechatMsg getWeChatMsg(String content, String wxid) {
        return null;
    }

    @Override
    public void getWeChatMsg(String command, String wxid, WechatBotClient client) {
        if ("开始游戏".equals(command)) {
            if (telnetClientDecorator != null) {
                client.sendMsg(new WechatMsg("已在游戏中", wxid, MsgType.SEND_TXT.TYPE()));
            } else {
                telnetClientDecorator = new TelnetClientExample(host, port, client, wxid);
                final Thread reader = new Thread(telnetClientDecorator);
                reader.start();
            }
            return;
        }
        if (telnetClientDecorator == null) {
            client.sendMsg(new WechatMsg("请先开始游戏", wxid, MsgType.SEND_TXT.TYPE()));
            return;
        } else if ("结束游戏".equals(command)) {
            telnetClientDecorator.stop();
            telnetClientDecorator = null;
        } else {
            telnetClientDecorator.sendMsg(command);
        }
    }
}


