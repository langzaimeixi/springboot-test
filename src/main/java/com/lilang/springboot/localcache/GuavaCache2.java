package com.lilang.springboot.localcache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * guava本地缓存
 *
 * User: lilang
 * Date: 2017/9/4 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@Getter
@Setter
public class GuavaCache2 implements Cache {
    private static final Object NULL_VALUE = new NullValue();

    private String name;

    private long maxSize = 100;

    private long expireTime = 24;

    private boolean allowNullValue = false;

    private com.google.common.cache.Cache<String, Object> cache;

    public GuavaCache2() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(this.maxSize)
                .expireAfterAccess(this.expireTime, TimeUnit.HOURS)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.cache;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object obj = cache.getIfPresent(key.toString());
        log.info("get from local cache is {}", obj);
        return (obj != null ? new SimpleValueWrapper(fromCacheValue(obj)) : null);
    }

    private Object fromCacheValue(Object object) {
        if (this.allowNullValue && object == NULL_VALUE ) return null;
        return object;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

   /* @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }*/

    @Override
    public void put(Object key, Object value) {
        if (!this.allowNullValue && value == null) {
            log.info("查询结果为空，不放入缓存");
            return;
        }
        this.cache.put(key.toString(), toCacheValue(value));
    }

    private Object toCacheValue(Object value) {
        if (this.allowNullValue && value == null) {
            log.info("将查询结果为空的值放入缓存");
            return NULL_VALUE;
        }
        return value;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        this.cache.invalidate(key.toString());
    }

    @Override
    public void clear() {
        this.cache.invalidateAll();
    }

    private static class NullValue implements Serializable {}
}
