package com.lilang.springboot.controller;

import com.lilang.springboot.model.TradeBaseInfoDO;
import com.lilang.springboot.service.TradeBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lilang Date: 2017/8/28 ProjectName: springboot-test
 */
@Slf4j
@RestController
public class TradeBaseInfoController {


    @Autowired
    private TradeBaseInfoService tradeBaseInfoService;

    @RequestMapping("/index")
    public List queryTradeBaseInfoModel(@RequestParam("costNo") String costNo) {
       // List list = new ArrayList<>();
        List list = new ArrayList();
        list.add(tradeBaseInfoService.queryTradeBaseInfoByCostNo(costNo));
        return list;
    }

    @RequestMapping("/index/update")
    public void updateTradeBaseInfo(TradeBaseInfoDO infoDO) {
        try {
            log.info("update tradeBaseInfo, param={}", infoDO);
            tradeBaseInfoService.updateById(infoDO);
        } catch (Exception e) {
            log.info("update tradeBaseInfo exception, e:", e);
        }
    }

}
