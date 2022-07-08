package cn.leexiaobu.wechatbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.leexiaobu.wechatbot.domain.Msg;
import cn.leexiaobu.wechatbot.service.MsgService;
import cn.leexiaobu.wechatbot.mapper.MsgMapper;
import org.springframework.stereotype.Service;

/**
* @author oujia
* @description 针对表【msg】的数据库操作Service实现
* @createDate 2022-07-07 15:09:35
*/
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg>
    implements MsgService{

}




