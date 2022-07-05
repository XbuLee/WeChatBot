package cn.leexiaobu.wechatbot.service.impl;


import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.common.WechatBotCommon;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import cn.leexiaobu.wechatbot.service.WechatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WechatBotServiceImpl implements WechatBotService, WechatBotCommon {


  /**
   * 注入微信客户端
   */
  @Autowired
  private WechatBotClient client;

  /**
   * 描述: 发送文字消息
   */
  @Override
  public void wechatCommon(WechatMsg wechatMsg) {
    // 消息类型
    client.sendMsg(wechatMsg);
  }

  /**
   * 描述: 发送文字消息
   */
  @Override
  public void sendTextMsg(WechatMsg wechatMsg) {
    wechatMsg.setType(TXT_MSG);
    // 消息类型
    client.sendMsg(wechatMsg);
  }

  /**
   * 描述: 发送图片消息
   */
  @Override
  public void sendImgMsg(WechatMsg wechatMsg) {
    wechatMsg.setType(PIC_MSG);
    client.sendMsg(wechatMsg);
  }

  /**
   * 描述: 群组内发送@指定人消息
   */
  @Override
  public void sendATMsg(WechatMsg wechatMsg) {
    wechatMsg.setType(AT_MSG);
    client.sendMsg(wechatMsg);
  }


  /**
   * 描述: 发送附件
   */
  @Override
  public void sendAnnex(WechatMsg wechatMsg) {
    wechatMsg.setType(ATTATCH_FILE);
    client.sendMsg(wechatMsg);
  }


  /**
   * 描述: 获取微信群组,联系人列表
   *
   * @see WechatBotCommon#USER_LIST 发起后会收到一条type类型是该常量值消息
   */
  @Override
  public void getWeChatUserList() {
    WechatMsg wechatMsg = new WechatMsg();
    wechatMsg.setType(USER_LIST);
    wechatMsg.setContent(CONTACT_LIST);
    client.sendMsg(wechatMsg);
  }

  /**
   * 描述:获取指定联系人的详细信息
   */
  @Override
  public void getPersonalDetail(String wxid) {
    WechatMsg wechatMsg = new WechatMsg();
    wechatMsg.setType(PERSONAL_DETAIL);
    client.sendMsg(wechatMsg);
  }

  @Override
  public void getChatroomMember(String roomId) {
    WechatMsg wechatMsg = new WechatMsg();
    wechatMsg.setType(MsgType.CHATROOM_MEMBER.TYPE());
    client.sendMsg(wechatMsg);
  }
}
