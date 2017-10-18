package com.lilang.springboot.lock;

/**
 * Created by lilang on 2017/8/1.
 */
public interface DistributeLock {
    boolean lock(String name);
    void release();
}
