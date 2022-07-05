package cn.leexiaobu.wechatbot.handler;

import cn.leexiaobu.wechatbot.api.XiaoAiTalker;
import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2022-07-04
 */
@Service
@Slf4j
public class SimpleMsgHandler implements MsgHandler {

  private XiaoAiTalker xiaoAiTalker = new XiaoAiTalker();

  @Override
  public void onMessage(WechatBotClient client, String s) {
    WechatMsg wechatMsg = JSONObject.parseObject(s, WechatMsg.class);
    MsgType type = MsgType.getType(wechatMsg.getType());
    log.debug("Receive msg,type:{}", type.name());
    switch (type) {
      case HEART_BEAT:
      case SEND_TXT_STATUS:
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
    if ("1730311071@chatroom".equals(wxid)) {
      String content = msg.getContent();
      if (content.startsWith("#")) {
        String txtResponse = xiaoAiTalker.getTxtResponse(content.replace("#", ""));
        WechatMsg send = new WechatMsg();
        send.setContent(txtResponse);
        send.setWxid(wxid);
        client.sendTxtMsg(send);
      }
    } else if ("wxid_q3po599n0zq522".equals(wxid)) {
      WechatMsg send = new WechatMsg();
      send.setContent("大家好，我是机器人小布");
      send.setWxid(wxid);
      client.sendTxtMsg(send);
    }
  }
}