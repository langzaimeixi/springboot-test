package com.lilang.springboot.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 *
 * User: lilang
 * Date: 2017/10/16 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@Service
public class RedisDistributeLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean lock(String key, String value) {

        log.info("acquiring lock, key={}, value={}", key, value);
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        RedisSerializer<String> valueSerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyEnum = keySerializer.serialize(key);
                byte[] valueEnum = valueSerializer.serialize(value);
                Boolean lock = false;
                try {
                    lock = connection.setNX(keyEnum, valueEnum);
                    if (lock) {
                        Boolean expireFlag = connection.expire(keyEnum, TimeUnit.MINUTES.toSeconds(5));
                        if (!expireFlag) {
                            connection.del(keyEnum);
                        }
                    }
                } catch (Exception e) {
                    log.error("acquiring lock exception: ", e);
                    if (lock) {
                        connection.del(keyEnum);
                    }
                } finally {
                    return lock;
                }
            }
        });
        return result;

    }

    public void release(String key, String value) {
        log.info("release lock, key={}, value={}", key, value);
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        RedisSerializer<String> valueSerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyEnum = keySerializer.serialize(key);
                byte[] valueEnum = valueSerializer.serialize(value);
                byte[][] keysAndArgs = new byte[2][];
                keysAndArgs[0] = keyEnum;
                keysAndArgs[1] = valueEnum;
                try {
                    String script = acquiringScript(RedisDistributeLock.class.getResource("/lua/ReleaseLock.lua").getFile());
                    String scriptSha = connection.scriptLoad(script.getBytes());
                    connection.evalSha(scriptSha, ReturnType.INTEGER, 1, keysAndArgs);
                    log.info("release lock success");
                } catch (Exception e) {
                    log.error("release lock exception: ", e);
                }
                return null;
            }
        });
    }

    private String acquiringScript(String scriptFilePath) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(new File(scriptFilePath)));
        StringBuffer b = new StringBuffer();
        while(true) {
            int ch = r.read();
            if(ch == -1) {
                r.close();
                return b.toString();
            }
            b.append((char)ch);
        }
    }

}
