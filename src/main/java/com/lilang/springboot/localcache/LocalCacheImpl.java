package com.lilang.springboot.localcache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 * User: lilang
 * Date: 2017/9/1 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@Service
public class LocalCacheImpl implements LocalCache {

    private Cache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(2)
            .expireAfterAccess(24, TimeUnit.HOURS)
            .recordStats()
            .build();

    private LoadingCache<String, Object> cache2 = CacheBuilder.newBuilder()
            .maximumSize(2)
            .expireAfterAccess(24, TimeUnit.HOURS)
            .recordStats()
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return null;
                }
            });

    /**
     * 从缓存中获取值
     *
     * @param key   缓存key
     * @return      缓存value
     */
    @Override
    public <T> T get(String key, Callable<T> callable) {
        T value = null;
        try {
            log.info("get from local cache, key:{}", key);
            value = (T)cache.get(key, callable);
        } catch (ExecutionException e) {
            log.error("get value from cache exception, e:", e);
            value = callable.call();
        } finally {
            return value;
        }
    }

    /**
     * 将值添加到缓存中
     *
     * @param key
     * @param value
     */
    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 清除指定的缓存
     *
     * @param key
     */
    @Override
    public void evit(String key) {
        try {
            cache2.invalidate(key);
        } catch (Exception e) {
            log.error("evit cache exception, e:", e);
        }

    }

    /**
     * 清空所有缓存
     */
    @Override
    public void evictAllCache() {
        cache.cleanUp();
    }

    @Override
    public <T> T getIfAbsent(String key) {
        T value = null;
        try {
            log.info("get from local cache, key={}", key);
            value = (T) cache2.getIfPresent(key);
        } catch (Exception e) {
            log.error("get value from cache exception, e:", e);
        } finally {
            log.info("get from local cache, value={}", value);
            return value;
        }
    }

    @Override
    public void put2(String key, Object obj) {
        cache2.put(key, obj);
    }

}
