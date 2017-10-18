package com.lilang.springboot.test;

import com.lilang.springboot.lock.RedisDistributeLock;
import com.lilang.springboot.start.ApplicationRun;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

/**
 * redis分布式锁测试
 *
 * User: lilang
 * Date: 2017/10/17 ProjectName: springboot-test Version：5.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRun.class)
@WebAppConfiguration
@Slf4j
public class RedisDistributeLockTest {

    @Autowired
    private RedisDistributeLock redisDistributeLock;

    @Test
    public void testLock() {
        String key = "lilang:lock:123456";
        String value = UUID.randomUUID().toString();
        boolean lock = redisDistributeLock.lock(key, value);
        if (lock) {
            log.info("获取锁成功");
            try {
                //处理业务
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("获取锁失败,e:", e);
            } finally {
                redisDistributeLock.release(key, value);
            }
        } else {
            log.info("获取锁失败");
        }
    }

}
