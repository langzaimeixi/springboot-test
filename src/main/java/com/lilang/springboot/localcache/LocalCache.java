package com.lilang.springboot.localcache;

import java.util.concurrent.Callable;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/1 ProjectName: springboot-test Version：5.0.0
 */
public interface LocalCache {
    public <T> T get(String key, Callable<T> callable);
    public void put(String key, Object value);
    public void evit(String key);
    public void evictAllCache();

    public <T> T getIfAbsent(String key);
    public void put2(String key, Object obj);

}
