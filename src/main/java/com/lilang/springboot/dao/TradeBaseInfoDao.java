package com.lilang.springboot.dao;

import com.lilang.springboot.model.TradeBaseInfoDO;
import org.springframework.stereotype.Repository;

/**
 * User: lilang Date: 2017/8/28 ProjectName: springboot-test
 */
@Repository
public interface TradeBaseInfoDao {

    TradeBaseInfoDO queryTradeBaseInfoByCostNo(String costNo);

    int update(TradeBaseInfoDO baseInfoDO);

    TradeBaseInfoDO getById(Long id);
}
