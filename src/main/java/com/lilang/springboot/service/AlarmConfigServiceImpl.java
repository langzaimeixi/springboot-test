package com.lilang.springboot.service;

import com.lilang.springboot.dao.AlarmConfigDao;
import com.lilang.springboot.model.AlarmConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "localCache", key = "'ALARM_CONFIG:'.concat(#alarmType)")
    public AlarmConfigDO queryAlarmConfigByAlarmType(final String alarmType) {
        //先从缓存中获取
        /*AlarmConfigDO alarmConfigDO = localCache.get(KEY_PREFIX + alarmType, new Callable<AlarmConfigDO>() {
            @Override
            public AlarmConfigDO call() throws Exception {
                log.info("query from localCache is null, query db, alarType:{}", alarmType);
                return alarmConfigDao.queryAlarmConfigByAlarmType(alarmType);
            }
        });*/
       /* String key = KEY_PREFIX + alarmType;
        AlarmConfigDO alarmConfigDO = localCache.getIfAbsent(key);
        if (null == alarmConfigDO) {
            log.info("get from local cache is null, query from db, key={}", key);
            alarmConfigDO = alarmConfigDao.queryAlarmConfigByAlarmType(alarmType);
            localCache.put2(key, alarmConfigDO);
        }
        return alarmConfigDO;*/
       log.info("从数据库中查询，alarmType={}", alarmType);
       return alarmConfigDao.queryAlarmConfigByAlarmType(alarmType);
    }

    @Override
    @CacheEvict(value = "localCache", key = "'ALARM_CONFIG:'.concat(#alarmConfigDO.alarmType)")
    public int updateAlarmConfig(AlarmConfigDO alarmConfigDO) {
        return alarmConfigDao.update(alarmConfigDO);
    }
}
