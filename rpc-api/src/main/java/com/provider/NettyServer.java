package com.provider;

import com.entity.ProviderBean;
import com.untils.IPAddressUntils;
import com.zk.ZKClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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

/**
 * @author CBeann
 * @create 2020-03-06 22:17
 * ApplicationContextAware:把applicationContext放在nettyServer
 * ApplicationListener:在ioc容器全部加载完毕后调用
 */
@Component
public class NettyServer implements InitializingBean, ApplicationListener<ApplicationEvent>, ApplicationContextAware {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap b = new ServerBootstrap();

    ApplicationContext applicationContext = null;

    ProviderConfigBean providerConfigBean = null;

    ZKClient zkClient = null;


    public NettyServer() {

    }

    public void bind(int port) {
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //TimeClientHandler是自己定义的方法
                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new MyServerHandler(applicationContext));
                        }
                    });
            System.out.println("Netty服务器启动成功:" + providerConfigBean.getProviderport());
            //绑定端口
            //ChannelFuture f = b.bind(port).sync();
            ChannelFuture f = b.bind(providerConfigBean.getProviderport()).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅关闭，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("赋值providerConfigBean...");

        this.providerConfigBean = applicationContext.getBean(ProviderConfigBean.class);


    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        new Thread(() -> {
            this.bind(providerConfigBean.getProviderport());
        }).start();


        //向zk暴露接口
        zkClient = new ZKClient(providerConfigBean.getZkUrl());
        doExport();


    }

    private void doExport() {
        ProviderBean providerBean = new ProviderBean();
        providerBean.setProviderName(providerConfigBean.getProviderName());
        providerBean.setInfername(providerConfigBean.getProviderIntername());
        String ip= IPAddressUntils.address();
        providerBean.setUrl(ip);
        providerBean.setPort(providerConfigBean.getProviderport());

        zkClient.sendProviderBeanMsg(providerBean);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
