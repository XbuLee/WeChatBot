package cn.leexiaobu.wechatbot.handler;

import cn.hutool.core.map.CaseInsensitiveMap;
import cn.leexiaobu.wechatbot.api.*;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.domain.Msg;
import cn.leexiaobu.wechatbot.domain.WeChatTxtMsg;
import cn.leexiaobu.wechatbot.config.MyEnvironmentUtil;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import cn.leexiaobu.wechatbot.mapper.MsgMapper;
import com.alibaba.fastjson.JSONObject;
import java.util.Arrays;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @date 2022-07-04
 */
@Service
@Slf4j
public class SimpleMsgHandler implements MsgHandler {
    @Autowired
    MsgMapper msgMapper;
  static CaseInsensitiveMap<String, CommonApi> instanceHashMap = new CaseInsensitiveMap<>();
  static HashMap<String, String> stringToCommand = new HashMap<>();
  HashSet<String> roomSet = new HashSet<>();

  public SimpleMsgHandler() {
    System.out.println("初始化");
    String apiString = MyEnvironmentUtil.getString("wechat.roomId");
    String[] split = apiString.split(",");
    roomSet.addAll(Arrays.asList(split));
  }

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

    //沙雕图
    stringToCommand.put("沙雕图", "sdt");
    stringToCommand.put("sdt", "sdt");
    instanceHashMap.put("sdt", new ShaDiaoTuApi());
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
        onRecTxtMsg(client, wechatMsg,s);
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

  private void onRecTxtMsg(WechatBotClient client, WechatMsg msg, String original) {
    String wxid = msg.getWxid();
    if (roomSet.contains(wxid)) {
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
            WeChatTxtMsg weChatTxtMsg = JSONObject.parseObject(original, WeChatTxtMsg.class);
            Msg msgDo = new Msg();
            msgDo.setSrvid(weChatTxtMsg.getSrvid());
            msgDo.setMsgid(weChatTxtMsg.getId());
            msgDo.setStatus(0);
            msgDo.setIssend(1);
            msgDo.setIsshowtimer(1);
            msgDo.setCreatetime(new Date());
            msgDo.setTalker(weChatTxtMsg.getId1());
            msgDo.setNickname(weChatTxtMsg.getId1());
            msgDo.setRoomid(weChatTxtMsg.getId2());
            msgDo.setContent(weChatTxtMsg.getContent());
            msgDo.setType(weChatTxtMsg.getType());
            msgMapper.insert(msgDo);
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