package com.provider;

import com.entity.rpc.RequestBean;
import com.entity.rpc.ResponseBean;
import com.untils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author CBeann
 * @create 2019-10-16 15:55
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    ApplicationContext applicationContext = null;

    public MyServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    //用一个ChannelGroup保存所有连接到服务器的客户端通道
    //private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        Channel channel = channelHandlerContext.channel();


        //服务器收到消息
        // "[服务端]   " + channel.remoteAddress() + "通道关闭";
        String body = s;
        System.out.println("接收到的参数： "+body);

        //测试请求
        RequestBean requestBean = new RequestBean();
//        requestBean.setRequestClass("com.service.StudentService");
//        requestBean.setRequestMethod("selectStudentById");
//        requestBean.setAtgsType(Integer.class);
//        requestBean.setArgs(1);
        //{"requestClass":"com.service.StudentService","requestMethod":"selectStudentById","args":1}
        requestBean = JsonUtils.jsonToPojo(body, RequestBean.class);


        //获取请求对象
        Class<?> aClass = Class.forName(requestBean.getRequestClass());
        Object bean = applicationContext.getBean(aClass);


        //获取方法
        Method method = aClass.getMethod(requestBean.getRequestMethod(), requestBean.getAtgsType());
//
//        //执行并且返回结果
        Object result = method.invoke(bean, requestBean.getArgs());

        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(200);
        responseBean.setData(result);

        System.out.println("请求响应的结果： "+responseBean.toString());


        channel.write("北京时间：" + new Date().toString());


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String notice = "[服务端]   " + channel.remoteAddress() + "通道激活";
        System.out.println(notice);
//        channelGroup.writeAndFlush(notice);
//        //添加建立连接的channel
//        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        Channel channel = ctx.channel();
//        //删除失效的channel
//        channelGroup.remove(channel);
//        String notice = "[服务端]   " + channel.remoteAddress() + "通道关闭";
//        channelGroup.writeAndFlush(notice);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[服务端]   " + channel.remoteAddress() + "出现异常");
        ctx.close();
    }
}