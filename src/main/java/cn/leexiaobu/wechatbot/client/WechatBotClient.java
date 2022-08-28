package cn.leexiaobu.wechatbot.client;

import static cn.leexiaobu.wechatbot.common.WechatBotCommon.EMPTY_MSG;
import static cn.leexiaobu.wechatbot.common.WechatBotCommon.NULL_MSG;
import static cn.leexiaobu.wechatbot.common.WechatBotCommon.TXT_MSG;

import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import cn.leexiaobu.wechatbot.handler.MsgHandler;
import com.alibaba.fastjson.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.util.StringUtils;

/**
 * websocket机器人客户端
 */
@Slf4j
public class WechatBotClient extends WebSocketClient {

    private MsgHandler msgHandler;

    /**
     * 描述: 构造方法创建 WechatBotClient对象
     */

    public WechatBotClient(String url, MsgHandler msgHandler) throws URISyntaxException {
        super(new URI(url));
        this.msgHandler = msgHandler;
    }

    /**
     * 描述: 在websocket连接开启时调用
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("初始化......");
    }

    /**
     * 描述: 方法在接收到消息时调用
     */
    @Override
    public void onMessage(String s) {
        msgHandler.onMessage(this, s);
    }

    /**
     * 描述: 方法在连接断开时调用
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("已断开连接... ");
    }

    /**
     * 描述: 方法在连接出错时调用
     */
    @Override
    public void onError(Exception e) {
        log.error("通信连接出现异常:" + e.getMessage());
    }

    public void sendTxtMsg(String content, String wxid) {
        sendMsg(new WechatMsg(content, wxid, MsgType.SEND_TXT.TYPE()));
    }

    public void sendPicMsg(String content, String wxid) {
        sendMsg(new WechatMsg(content, wxid, MsgType.SEND_PIC.TYPE()));
    }

    /**
     * 描述: 发送消息工具 (其实就是把几行常用代码提取出来 )
     */
    public void sendMsg(WechatMsg wechatMsg) {
        if (!StringUtils.hasText(wechatMsg.getExt())) {
            wechatMsg.setExt(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getNickname())) {
            wechatMsg.setNickname(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getRoomid())) {
            wechatMsg.setRoomid(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getContent())) {
            wechatMsg.setContent(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getWxid())) {
            wechatMsg.setWxid(NULL_MSG);
        }
        // 消息Id
        wechatMsg.setId(String.valueOf(System.currentTimeMillis()));
        // 发送消息
        log.info("发送消息 {}", wechatMsg.toString().replace("\n",""));
        send(JSONObject.toJSONString(wechatMsg));
    }
}