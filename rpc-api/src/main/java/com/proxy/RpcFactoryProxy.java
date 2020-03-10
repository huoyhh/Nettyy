package com.proxy;

/**
 * @author CBeann
 * @create 2020-03-09 21:21
 */

import com.consumer.NettyClient;
import com.entity.rpc.RequestBean;
import com.untils.JsonUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author CBeann
 * @create 2020-03-09 17:37
 */
public class RpcFactoryProxy<T> implements InvocationHandler {

    private Class<T> proxyInterface;
    //这里可以维护一个缓存，存这个接口的方法抽象的对象

    private NettyClient nettyClient;



    public RpcFactoryProxy(Class<T> proxyInterface, NettyClient nettyClient) {
        this.proxyInterface = proxyInterface;
        this.nettyClient = nettyClient;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //String result = (String) method.invoke(proxyInterface,args);
        //这里可以得到方法抽象对象来调用真的的查询方法

        RequestBean requestBean = new RequestBean();
        //设置请求方法名称
        String name = method.getName();
        requestBean.setRequestMethod(name);
        //设置请求参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        requestBean.setAtgsType(parameterTypes[0]);
        //设置请求参数
        requestBean.setArgs(args[0]);
        //设置请求累
        requestBean.setRequestClass(proxyInterface.getName());

        String s = JsonUtils.objectToJson(requestBean);

        nettyClient.sendMessage(s);


        return null;
    }

    public T getProxy() {
        return (T) Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[]{proxyInterface}, this);
    }
}
