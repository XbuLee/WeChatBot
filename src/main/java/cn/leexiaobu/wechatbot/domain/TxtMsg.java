package cn.leexiaobu.wechatbot.domain;

/**
 * @date 2022-07-05
 */
public class TxtMsg  {

  //等于个人wxid
  private String id1;
  //等于wxid
  private String id2;
  //验证？
  private String id3;
  //一直是1？
  private String srvid;

  private String time;
  private String wxid;
  //1
  private int type;

//{"content":"可以","id":"20220705104149","id1":"wxid_2317673176015","id2":"19306143461@chatroom",
// "id3":"<msgsource>\n\t<silence>1</silence>\n\t<membercount>30</membercount>\n\t<signature>v1_+e0WEZ5o</signature>\n</msgsource>\n",
// "srvid":1,"time":"2022-07-05 10:41:49","type":1,"wxid":"19306143461@chatroom"}
}