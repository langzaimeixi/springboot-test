package com.lilang.springboot.dao;


import com.lilang.springboot.model.AlarmConfigDO;
import org.springframework.stereotype.Repository;

/**
 * 告警配置dal层方法
 *
 * User: lilang  Date: 2017/8/8 ProjectName: cost Version: 1.0
 */
@Repository
public interface AlarmConfigDao {

    /**
     * 通过告警类型查询告警配置信息
     *
     * @param alarmType     告警类型
     * @return             告警配置信息
     */
    AlarmConfigDO queryAlarmConfigByAlarmType(String alarmType);

    /**
     * 更新告警配置信息
     *
     * @param alarmConfigDO
     * @return
     */
    int update(AlarmConfigDO alarmConfigDO);
}