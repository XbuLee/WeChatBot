package cn.leexiaobu.wechatbot.handler;

import cn.hutool.core.map.CaseInsensitiveMap;
import cn.leexiaobu.wechatbot.api.*;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @date 2022-07-04
 */
@Service
@Slf4j
public class SimpleMsgHandler implements MsgHandler {
    static CaseInsensitiveMap<String, CommonApi> instanceHashMap = new CaseInsensitiveMap<>();
    static HashMap<String, String> stringToCommand = new HashMap<>();

    static {
        //默认小爱同学
        instanceHashMap.put("xm", new DefaultApi());
        //笑话
        stringToCommand.put("xh", "xh");
        stringToCommand.put("笑话", "xh");
        instanceHashMap.put("xh", new XiaoHuaApi());
        //图片
        stringToCommand.put("ldst", "ldst");
        stringToCommand.put("来点色图", "ldst");
        instanceHashMap.put("ldst", new ImageApi());
        //天气
        stringToCommand.put("天气", "tq");
        stringToCommand.put("tq", "tq");
        instanceHashMap.put("tq", new TianQiApi());

        //情话
        stringToCommand.put("情话", "qh");
        stringToCommand.put("qh", "qh");
        instanceHashMap.put("qh", new QinghuaApi());
    }


    @Override
    public void onMessage(WechatBotClient client, String s) {
        WechatMsg wechatMsg = JSONObject.parseObject(s, WechatMsg.class);
        MsgType type = MsgType.getType(wechatMsg.getType());
        log.debug("Receive msg,type:{}", type.name());
        switch (type) {
            case HEART_BEAT:
            case SEND_TXT:
                log.debug(JSONObject.toJSONString(wechatMsg));
                break;
            case REC_TXT_MSG:
                log.info("文本原始数据:{}", s);
                onRecTxtMsg(client, wechatMsg);
                break;
            case REC_PIC_MSG:
                log.info("图片原始数据:{}", s);
                break;
            case UNKNOW_TYPE:
            default:
                log.info("未知数据:{}", s);
                log.error("Unknown msg type {}", JSONObject.toJSONString(wechatMsg));
        }
    }

    private void onRecTxtMsg(WechatBotClient client, WechatMsg msg) {
        String wxid = msg.getWxid();
        if ("1730311071@chatroom".equals(wxid) || "23177834269@chatroom".equals(wxid)) {
            String content = msg.getContent();
            if (content.startsWith("#")) {
                log.info("收到指令：" + content);
                String command = content.replace("#", "");
                if ("help".equals(command)) {
                    String help = instanceHashMap.entrySet().stream()
                            .filter(key -> !"xm".equals(key.getKey()))
                            .map(entry -> "#" + entry.getKey())
                            .collect(Collectors.joining("\n"));
                    WechatMsg weChatMsg = new WechatMsg(help, wxid, MsgType.SEND_TXT.TYPE());
                    client.sendMsg(weChatMsg);
                    return;
                }
                CommonApi apiInstance = getApiInstance(command);
                WechatMsg weChatMsg = apiInstance.getWeChatMsg(command, wxid);
                client.sendMsg(weChatMsg);
            }
        }
    }

    //通过命令查找对应的API
    private CommonApi getApiInstance(String content) {
        String[] result = content.split(" ");
        String realContent = result[0];
        String command = stringToCommand.get(realContent);
        if (command == null) {
            return instanceHashMap.get("xm");
        } else {
            return instanceHashMap.get(command);
        }
    }
}