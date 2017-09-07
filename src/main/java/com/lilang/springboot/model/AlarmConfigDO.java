package com.lilang.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class AlarmConfigDO {

    private static final long serialVersionUID = -7658923257757971670L;

    /**
     * 告警类型
     */
    private String alarmType;

    /**
     * 告警主题
     */
    private String alarmSubject;

    /**
     * 发送短信标识
     */
    private String smsFlag;

    /**
     * 短信接收方,英文逗号隔开
     */
    private String smsReceiver;

    /**
     * 发送邮件标识
     */
    private String mailFlag;

    /**
     * 邮件接收方,英文逗号隔开
     */
    private String mailReceiver;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 最后更新人
     */
    private String updatedBy;

    /**
     * 可用标识
     */
    private String usableFlag;

}