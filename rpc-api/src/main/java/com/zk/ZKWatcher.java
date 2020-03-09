package com.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author CBeann
 * @create 2020-03-06 23:20
 */
public class ZKWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 获取事件的状态
        Event.KeeperState keeperState = watchedEvent.getState();
        Event.EventType eventType = watchedEvent.getType();
        // 如果是建立连接
        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                System.out.println("zk 建立连接");
            }
        }
    }
}
