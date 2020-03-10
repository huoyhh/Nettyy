package com;

import com.annotation.EnableProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author CBeann
 * @create 2020-03-06 22:43
 */
@EnableProvider
public class Rpc_provider_main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Rpc_provider_main.class);

//        NettyServer bean = context.getBean(NettyServer.class);
//        System.out.println(bean);


        System.out.println("--(rpc-provider-main 正常启动)--");
        System.in.read();
    }
}
