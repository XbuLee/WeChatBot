package cn.leexiaobu.wechatbot.service;


import cn.leexiaobu.wechatbot.domain.WechatMsg;

public interface WechatBotService {

  /**
   * 描述: 发送文字消息
   */
  public void wechatCommon(WechatMsg wechatMsg);

  /**
   * 描述: 发送文字消息
   */
  public void sendTextMsg(WechatMsg wechatMsg);

  /**
   * 描述: 发送图片消息
   */
  public void sendImgMsg(WechatMsg wechatMsg);

  /**
   * 描述: 群组内发送@指定人消息
   */
  void sendATMsg(WechatMsg wechatMsg);


  /**
   * 描述: 发送附件
   */
  void sendAnnex(WechatMsg wechatMsg);

  /**
   * 描述: 获取微信群组,联系人列表
   */
  void getWeChatUserList();

  /**
   * 描述:获取指定联系人的详细信息
   */
  void getPersonalDetail(String wxid);


  void getChatroomMember(String roomId);

}
