package com.lilang.springboot.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lilang on 2017/8/1.
 */
@Slf4j
@Service
public class ZKDistributeLock implements DistributeLock, Watcher {
    private static final String URL = "10.0.21.56:2181";
    private static final int SESSION_TIMEOUT = 10000;
    private String root = "/locks";
    private ThreadLocal<String> currNodeLocal = new ThreadLocal<>();
    private ThreadLocal<String> waitNodeLocal = new ThreadLocal<>();
    private ThreadLocal<CountDownLatch> latchThreadLocal = new ThreadLocal<>();
    private ZooKeeper zk;

    @PostConstruct
    public void init() {
        try {
            zk = new ZooKeeper(URL, SESSION_TIMEOUT, this);
            Stat stat = zk.exists(root, false); //判断是不是已经存在locks节点，不需要监听root节点
            if (stat == null) { //如果不存在，则创建根节点
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            log.info("init zk connection err:{}", e);
        }
    }

    @Override
    public boolean lock(String name) {
        if (tryLock(name)) {
            return true;
        }
        try {
            return waitLock(waitNodeLocal.get(), SESSION_TIMEOUT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void release() {
        System.out.println(Thread.currentThread().getName()+"-->"+currNodeLocal.get()+"释放锁");
        try {
            zk.delete(currNodeLocal.get(), -1);
            zk.close();
        } catch (Exception e) {
           log.info("release lock exception, e:{}", e);
        }

    }

    private boolean tryLock(String name) {
        String splitStr = name; //lock_0000000001
        try {
            //创建一个有序的临时节点，赋值给myznode
            String currNode = zk.create(root + "/" + splitStr, new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            currNodeLocal.set(currNode);
            System.out.println(Thread.currentThread().getName()+"-->"+currNode + " 创建成功");
            List<String> subNodes = zk.getChildren(root, false);
            Collections.sort(subNodes); //讲所有的子节点排序
            if (currNode.equals(root + "/" + subNodes.get(0))) {
                //当前客户端创建的临时有序节点是locks下节点中的最小的节点，表示当前的客户端能够获取到锁
                return true;
            }
            //否则的话,监听比自己小的节点 locks/lock_0000000003
            String subMyZnode = currNode.substring((currNode.lastIndexOf("/") + 1));
            String waitNode = subNodes.get(Collections.binarySearch(subNodes, subMyZnode) - 1);// 获取比当前节点小的节点
            waitNodeLocal.set(waitNode);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean waitLock(String lower, long waitTime) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(root + "/" + lower, true); //获取节点状态，并添加监听
        if (stat != null) {
            this.latchThreadLocal.set(new CountDownLatch(1)); //实例化计数器，让当前的线程等待
            this.latchThreadLocal.get().await(waitTime, TimeUnit.MILLISECONDS);
            this.latchThreadLocal.set(null);
        }
        return true;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (this.latchThreadLocal.get() != null) {
            this.latchThreadLocal.get().countDown();
        }
    }
}
