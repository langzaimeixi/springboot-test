package com.lilang.springboot.service;

import com.lilang.springboot.cache.annotation.CacheEvit;
import com.lilang.springboot.cache.annotation.CachePut;
import com.lilang.springboot.cache.annotation.Param;
import com.lilang.springboot.cache.annotation.Response;
import com.lilang.springboot.dao.TradeBaseInfoDao;
import com.lilang.springboot.model.TradeBaseInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: lilang Date: 2017/8/28 ProjectName: cost
 */

@Slf4j
@Service
public class TradeBaseInfoServiceImpl implements TradeBaseInfoService {

    @Autowired
    private TradeBaseInfoDao tradeBaseInfoDao;

    @CachePut(key = "com.lilang.springboot.util.KeyBuildUtil.buildKey(" +
            "com.lilang.springboot.constant.CacheConstant.TRADE_BASE_PREFIX,costNo)")
    public TradeBaseInfoDO queryTradeBaseInfoByCostNo(@Param("costNo") String costNo) {
        log.info("get from local cache is null, get from db");
        return tradeBaseInfoDao.queryTradeBaseInfoByCostNo(costNo);
    }

    @CacheEvit(keys = {"'test:tradeBaseInfo:costNo:' + baseInfoDO.costNo"})
    public @Response("baseInfoDO") TradeBaseInfoDO updateById(@Param("baseInfoDO") TradeBaseInfoDO baseInfoDO)
            throws Exception{
        TradeBaseInfoDO orignBaseInfoDO = tradeBaseInfoDao.getById(baseInfoDO.getId());
        if (orignBaseInfoDO == null) {
            throw new Exception("TradeBaseInfo_not_exist");
        }
        tradeBaseInfoDao.update(baseInfoDO);
        return orignBaseInfoDO;
    }


}
