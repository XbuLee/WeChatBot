package cn.leexiaobu.wechatbot.domain;

import lombok.Data;

@Data
public class WeChatTxtMsg extends WechatMsg {
    //一般为发送人的wxid
    private String id1;
    //群聊显示的是房间的id
    private String id2;
    //未知意义
    private String srvid;
}
