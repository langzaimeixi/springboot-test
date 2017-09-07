package com.lilang.springboot.service;

import com.lilang.springboot.cache.annotation.CacheEvit;
import com.lilang.springboot.cache.annotation.CachePut;
import com.lilang.springboot.cache.annotation.Param;
import com.lilang.springboot.dao.AlarmConfigDao;
import com.lilang.springboot.model.AlarmConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/1 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@Service
public class AlarmConfigServiceImpl implements AlarmConfigService {
    private static final String KEY_PREFIX = "ALARM_CONFIG:";

    @Autowired
    private AlarmConfigDao alarmConfigDao;

    @Override
    @CachePut(key = "'ALARM_CONFIG:' + alarmType")
    public AlarmConfigDO queryAlarmConfigByAlarmType(@Param("alarmType") String alarmType) {
       log.info("从数据库中查询，alarmType={}", alarmType);
       return alarmConfigDao.queryAlarmConfigByAlarmType(alarmType);
    }

    @Override
    @CacheEvit(key = "'ALARM_CONFIG' + alarmConfigDO.alarmType")
    public int updateAlarmConfig(@Param("alarmConfigDO") AlarmConfigDO alarmConfigDO) {
        return alarmConfigDao.update(alarmConfigDO);
    }
}
