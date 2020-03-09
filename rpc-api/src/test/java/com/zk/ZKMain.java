package com.zk;

import com.entity.ProviderBean;

/**
 * @author CBeann
 * @create 2020-03-06 23:40
 */
public class ZKMain {
    public static void main(String[] args) throws Exception {


        ZKClient zkClient = new ZKClient("39.105.30.146:2181");
        ProviderBean providerBean = new ProviderBean();
        providerBean.setProviderName("user-provider2");
        providerBean.setInfername("com.imooc.UserService");
        providerBean.setUrl("123");
        providerBean.setPort(8888);
        zkClient.sendProviderBeanMsg(providerBean);


//        ConsumerBean consumerBean = new ConsumerBean();
//        consumerBean.setConsumerName("order-cunsumer2");
//        zkClient.sendConsumerBeannMsg(consumerBean);

//        List<ProviderBean> providerBeanList = zkClient.getProviderBeanList();
//        for (ProviderBean providerBean : providerBeanList) {
//            System.out.println(providerBean);
//        }


//        List<ConsumerBean> consumerBeanList = zkClient.getConsumerBeanList();
//        for (ConsumerBean consumerBean : consumerBeanList) {
//            System.out.println(consumerBean);
//        }

        System.out.println("--over---");
        System.in.read();


        //zkClient.closeZk();


    }
}
