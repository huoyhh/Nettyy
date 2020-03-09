package com.consumer;

import com.entity.rpc.ConsumerBean;
import com.entity.rpc.ProviderBean;
import com.zk.ZKClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CBeann
 * @create 2020-03-06 22:17
 */
@Component
public class NettyClient implements ApplicationContextAware, InitializingBean, ApplicationListener<ApplicationEvent> {

    ApplicationContext applicationContext = null;

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();

    ChannelFuture f = null;

    ZKClient zkClient = null;

    ConsumerConfigBean consumerConfigBean = null;


    ProviderBean providerBean = null;


    public void bind(String url, Integer port) {
       // url = "127.0.0.1";
       // port = 8888;
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            //TimeClientHandler是自己定义的方法
                            socketChannel.pipeline().addLast(new MyChatClientHandler());
                        }
                    });
            //发起异步连接操作
            System.out.println("netty clinet 启动成功：" + port);
            f = b.connect(url, port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        f.channel().writeAndFlush(msg);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.consumerConfigBean = applicationContext.getBean(ConsumerConfigBean.class);
        //连接zk
        zkClient = new ZKClient(consumerConfigBean.getZkUrl());
        List<ProviderBean> providerBeanList = zkClient.getProviderBeanList();
        if (providerBeanList!=null&&providerBeanList.size()>0){
            this.providerBean = providerBeanList.get(0);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("onApplicationEvent");

        bind(providerBean.getUrl(), providerBean.getPort());

        //向zk暴露接口
        doExport();
    }

    private void doExport() {
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setConsumerName(consumerConfigBean.getConsumerName());
        zkClient.sendConsumerBeannMsg(consumerBean);
    }
}
