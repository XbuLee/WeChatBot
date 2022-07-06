package cn.leexiaobu.wechatbot.enums;

/**
 * @date 2022-07-04
 */

public enum MsgType {
  //心跳
  HEART_BEAT(5005),
  USER_LIST(5000),
  GET_USER_LIST_SUCCESS(5001),
  GET_USER_LIST_FAIL(5002),
  ATTACH_FILE(5003),
  CHATROOM_MEMBER(5010),
  CHATROOM_MEMBER_NICK(5020),
  PERSONAL_INFO(6500),
  DESTROY_ALL(9999),
  ADD_FRIEND(37),
  AGREE_TO_ADD_FRIEND(10000),
  //发送文字消息
  SEND_TXT(555),
  SEND_PIC(500),
  SEND_AT(550),
  //接收文字消息
  REC_TXT_MSG(1),
  REC_GROUP_MSG(49),
  REC_PIC_MSG(3),
  UNKNOW_TYPE(-1);
  private int type;

  private MsgType(int i) {
    this.type = i;
  }

  public static String name(int i) {
    for (MsgType value : values()) {
      if (value.type == i) {
        return value.name();
      }
    }
    return UNKNOW_TYPE.name();
  }

  public static MsgType getType(int i) {
    return MsgType.valueOf(MsgType.name(i));
  }

  public int TYPE() {
    return this.type;
  }

}
