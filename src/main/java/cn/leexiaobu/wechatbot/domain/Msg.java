package cn.leexiaobu.wechatbot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName msg
 */
@TableName(value ="msg")
@Data
public class Msg implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String srvid;

    /**
     * 
     */
    private String msgid;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Integer issend;

    /**
     * 
     */
    private Integer isshowtimer;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private String talker;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String roomid;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String imgpath;

    /**
     * 
     */
    private String reserved;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Msg other = (Msg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSrvid() == null ? other.getSrvid() == null : this.getSrvid().equals(other.getSrvid()))
            && (this.getMsgid() == null ? other.getMsgid() == null : this.getMsgid().equals(other.getMsgid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIssend() == null ? other.getIssend() == null : this.getIssend().equals(other.getIssend()))
            && (this.getIsshowtimer() == null ? other.getIsshowtimer() == null : this.getIsshowtimer().equals(other.getIsshowtimer()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getTalker() == null ? other.getTalker() == null : this.getTalker().equals(other.getTalker()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getRoomid() == null ? other.getRoomid() == null : this.getRoomid().equals(other.getRoomid()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getImgpath() == null ? other.getImgpath() == null : this.getImgpath().equals(other.getImgpath()))
            && (this.getReserved() == null ? other.getReserved() == null : this.getReserved().equals(other.getReserved()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSrvid() == null) ? 0 : getSrvid().hashCode());
        result = prime * result + ((getMsgid() == null) ? 0 : getMsgid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIssend() == null) ? 0 : getIssend().hashCode());
        result = prime * result + ((getIsshowtimer() == null) ? 0 : getIsshowtimer().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getTalker() == null) ? 0 : getTalker().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getRoomid() == null) ? 0 : getRoomid().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getImgpath() == null) ? 0 : getImgpath().hashCode());
        result = prime * result + ((getReserved() == null) ? 0 : getReserved().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", srvid=").append(srvid);
        sb.append(", msgid=").append(msgid);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", issend=").append(issend);
        sb.append(", isshowtimer=").append(isshowtimer);
        sb.append(", createtime=").append(createtime);
        sb.append(", talker=").append(talker);
        sb.append(", nickname=").append(nickname);
        sb.append(", roomid=").append(roomid);
        sb.append(", content=").append(content);
        sb.append(", imgpath=").append(imgpath);
        sb.append(", reserved=").append(reserved);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}