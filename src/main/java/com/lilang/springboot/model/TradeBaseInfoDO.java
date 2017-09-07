package com.lilang.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: lilang Date: 2017/8/28 ProjectName: cost
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TradeBaseInfoDO implements Serializable{
    private static final long serialVersionUID = 4924978093769981324L;

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
     * 成本业务单号
     */
    private String costNo;

    /**
     * 请求业务订单号
     * <p>
     * 对于业务订单号。如：5.0清分系统的清分单号；征信系统的channelOrderNo
     * </p>
     */
    private String requestNo;

    /**
     * 请求渠道单号
     * <p>
     * 对应请求渠道的唯一编号(reqNo)，如：5.0请求银行的reqNo；征信请求渠道的reqNo
     * </p>
     */
    private String requestChannelNo;

    /**
     * 请求系统
     * <p>表明该成本交易谁有那个平台发起。同owned， 所属机构，取值OwnedTypeEnum</p>
     */
    private String requestSystem;

    /**
     * 交易请求时间
     * <p>表明成本交易的时间</p>
     */
    private Date requestDate;

    /**
     * 交易主体类型
     * <p></p>
     */
    private String tradeEntityType;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 柜台号
     */
    private String counterNo;

    /**
     * 资金渠道
     */
    private String channelNo;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 支付金额
     */
    private long amt;

    /**
     * 成本金额
     */
    private BigDecimal feeAmt;

    /**
     * 交易状态
     * 状态， I:交易请求， S:交易成功， F:交易失败，P:受理成功
     */
    private String status;

    /**
     * 计费标示（是否计费，TRUE：计费，FALSE：未计费）
     */
    private String feeFlag;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 手续费同步标识
     */
    private String syncFlag;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 日志跟踪号
     */
    private String tradeLogId;

    /**
     * 备注
     */
    private String description;

    /**
     * 是否可用
     */
    private String usableFlag;
}
