package com.lilang.springboot.service;

import com.lilang.springboot.model.TradeBaseInfoDO;

/**
 * User: lilang Date: 2017/8/28 ProjectName: cost
 */
public interface TradeBaseInfoService {
    TradeBaseInfoDO queryTradeBaseInfoByCostNo(String costNo);

    TradeBaseInfoDO updateById(TradeBaseInfoDO baseInfoDO) throws Exception;
}
