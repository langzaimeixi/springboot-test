package com.lilang.springboot.controller;

import com.lilang.springboot.model.AlarmConfigDO;
import com.lilang.springboot.service.AlarmConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 告警配置controller
 *
 * User: lilang
 * Date: 2017/9/1 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@RestController
public class AlarmConfigController {

    @Autowired
    private AlarmConfigService alarmConfigService;

    @RequestMapping("/alarmConfig/query")
    public Map<String, Object> queryAlarmConfig(String alarmType) {
        Map<String, Object> map = new HashMap<String, Object>();
        log.info("alarmConfigService.queryAlarmConfigByAlarmType, alarmType:{}", alarmType);
        AlarmConfigDO alarmConfigDO = alarmConfigService.queryAlarmConfigByAlarmType(alarmType);
        log.info("alarmConfigService.queryAlarmConfigByAlarmType, result:{}", alarmConfigDO);
        map.put(alarmType, alarmConfigDO);
        return map;
    }

}
