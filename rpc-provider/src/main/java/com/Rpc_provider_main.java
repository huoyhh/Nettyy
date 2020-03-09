package com;

import com.consumer.ConsumerConfigBean;
import com.consumer.NettyClient;
import com.provider.NettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author CBeann
 * @create 2020-03-06 22:43
 */
@ComponentScan(value = "com",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ConsumerConfigBean.class, NettyClient.class})})
public class Rpc_provider_main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Rpc_provider_main.class);

        NettyServer bean = context.getBean(NettyServer.class);
        System.out.println(bean);





        System.out.println("--OVER--");
        System.in.read();
    }
}
