package cn.leexiaobu.wechatbot.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @date 2022-07-04
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

  private String id;
  private Integer srvid;
  private String time;
  private String wxid;
  private String content;
  private String sender;
  private Integer type;
}