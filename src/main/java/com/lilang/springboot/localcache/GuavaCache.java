package com.lilang.springboot.localcache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/4 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
public class GuavaCache implements Cache {

    @Autowired
    private LocalCache localCache;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.localCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        String keyf = key.toString();
        Object obj = localCache.getIfAbsent(keyf);
        return (obj != null ? new SimpleValueWrapper(obj) : null);
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
        this.localCache.put2(key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        this.localCache.evit(key.toString());
    }

    @Override
    public void clear() {
        this.localCache.evictAllCache();
    }

}
