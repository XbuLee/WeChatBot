package cn.leexiaobu.wechatbot.controller;

import cn.leexiaobu.wechatbot.common.util.AjaxResult;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.service.WechatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WechatBotController {

    @Autowired
    private WechatBotService wechatBotService;


    /**
     * 描述: 通用请求接口
     */
    @PostMapping("/wechatCommon")
    public AjaxResult wechatCommon(@RequestBody WechatMsg wechatMsg) {
        wechatBotService.wechatCommon(wechatMsg);
        return AjaxResult.success();
    }


    /**
     * 描述: 发送文本消息
     */
    @PostMapping("/sendTextMsg")
    public AjaxResult sendTextMsg(@RequestBody WechatMsg wechatMsg) {
        wechatBotService.sendTextMsg(wechatMsg);
        return AjaxResult.success();
    }

    /**
     * 描述: 发送图片消息
     *
     */
    @PostMapping("/sendImgMsg")
    public AjaxResult sendImgMsg(@RequestBody WechatMsg wechatMsg) {
        // 发送消息
        wechatBotService.sendImgMsg(wechatMsg);
        return AjaxResult.success();
    }

    /**
     * 描述: 群组内发送@指定人消息(dll 3.1.0.66版本不可用)
     */
    @PostMapping("/sendATMsg")
    public AjaxResult sendATMsg(@RequestBody WechatMsg wechatMsg) {
        wechatBotService.sendATMsg(wechatMsg);
        return AjaxResult.success();
    }

    /**
     * 描述: 发送附件
     */
    @PostMapping("/sendAnnex")
    public AjaxResult sendAnnex(@RequestBody WechatMsg wechatMsg) {
        wechatBotService.sendAnnex(wechatMsg);
        return AjaxResult.success();
    }


    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取信息 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 描述: 获取微信群组,联系人列表
     *
     */
    @GetMapping("/getWeChatUserList")
    public AjaxResult getWeChatUserList() {
        wechatBotService.getWeChatUserList();
        return AjaxResult.success();
    }
    @GetMapping("/getWeChatUserList/{roomId}")
    public AjaxResult getRoomMember(@PathVariable("roomId") String roomId) {
        wechatBotService.getChatroomMember(roomId);
        return AjaxResult.success();
    }
    /**
     * 描述: 获取个人详细信息 3.2.2.121版本dll 未提供该接口
     *

     */
     @GetMapping("/getPersonalDetail/{wxid}")
    public AjaxResult getPersonalDetail(@PathVariable("wxid") String wxid) {
        wechatBotService.getPersonalDetail(wxid);
        return AjaxResult.success();
    }
}
