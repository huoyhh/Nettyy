package com.zk;

import com.entity.rpc.ConsumerBean;
import com.entity.rpc.ProviderBean;
import com.untils.JsonUtils;
import org.apache.zookeeper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author CBeann
 * @create 2020-03-06 23:06
 */
public class ZKClient {

    //根目录
    private static final String path = "/myrpc/";
    //服务提供者
    private static final String path_provider = "/myrpc/provider";
    //服务消费者
    private static final String path_consumer = "/myrpc/consumer";


    /**
     * zookeeper地址
     */
    String CONNECT_ADDR = "39.105.30.146:2181";
    /**
     * session超时时间
     */
    static final int SESSION_OUTTIME = 2000;// ms
    /**
     * 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号
     */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    ZooKeeper zk;


    public ZKClient(String url) {
        this.CONNECT_ADDR = url;
        try {
            zk = new ZooKeeper(url, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    // 获取事件的状态
                    Event.KeeperState keeperState = event.getState();
                    Event.EventType eventType = event.getType();
                    // 如果是建立连接
                    if (Event.KeeperState.SyncConnected == keeperState) {
                        if (Event.EventType.None == eventType) {
                            // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                            System.out.println("zk 建立连接");
                            connectedSemaphore.countDown();
                        }
                    }
                }
            });

            // 进行阻塞
            connectedSemaphore.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //向zk注册服务提供者
    public void sendProviderBeanMsg(ProviderBean providerBean) {


        String s = JsonUtils.objectToJson(providerBean);
        try {
            String path1 = zk.create(path_provider + "/" + providerBean.getProviderName(), s.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            System.out.println("Success create path: " + path1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //获取服务提供者集合
    public List<ProviderBean> getProviderBeanList() {

        List<ProviderBean> providerBeanList = new ArrayList<>();

        try {
            List<String> children = zk.getChildren(path_provider, null);
            for (String child : children) {
                byte[] data = zk.getData(path_provider + "/" + child, false, null);
                String json = new String(data);
                ProviderBean providerBean = JsonUtils.jsonToPojo(json, ProviderBean.class);
                providerBeanList.add(providerBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return providerBeanList;
    }

    //获取服务消费者集合
    public List<ConsumerBean> getConsumerBeanList() {
        List<ConsumerBean> consumerBeanList = new ArrayList<>();

        try {
            List<String> children = zk.getChildren(path_consumer, null);
            for (String child : children) {

                byte[] data = zk.getData(path_consumer + "/" + child, false, null);
                String json = new String(data);
                ConsumerBean providerBean = JsonUtils.jsonToPojo(json, ConsumerBean.class);
                consumerBeanList.add(providerBean);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return consumerBeanList;
    }

    //向zk注册服务消费者
    public void sendConsumerBeannMsg(ConsumerBean consumerBean) {

        String s = JsonUtils.objectToJson(consumerBean);
        try {
            String path1 = zk.create(path_consumer + "/" + consumerBean.getConsumerName(), s.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            System.out.println("Success create path: " + path1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //关闭zk
    public void closeZk() {
        try {
            zk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
