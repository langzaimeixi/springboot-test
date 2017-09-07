package com.lilang.springboot.service;

import com.lilang.springboot.model.AlarmConfigDO;

import java.util.List;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/1 ProjectName: springboot-test Version：5.0.0
 */
public interface AlarmConfigService {
    AlarmConfigDO queryAlarmConfigByAlarmType(String alarmType);

    int updateAlarmConfig(AlarmConfigDO alarmConfigDO);
}
