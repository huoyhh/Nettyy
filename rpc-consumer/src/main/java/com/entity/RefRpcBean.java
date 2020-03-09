package com.entity;

import com.consumer.ConsumerConfigBean;
import com.consumer.NettyClient;
import com.proxy.RpcFactoryProxy;
import com.service.StudentService;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author CBeann
 * @create 2020-03-09 19:27
 */
@Data
@Component("studentService")
public class RefRpcBean implements FactoryBean, ApplicationContextAware {

    private String classname;

    private ApplicationContext applicationContext = null;

    private Object ref = null;

    @Autowired
    private NettyClient nettyClient;


    @Override
    public Object getObject() throws Exception {
        Object proxy = null;
        try {
            RpcFactoryProxy factoryProxy = new RpcFactoryProxy(Class.forName(classname), nettyClient);
            proxy = factoryProxy.getProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy;

//        Student student = new Student();
//        student.setName("refRpcBean");
//        student.setId(1);
//        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return StudentService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        ConsumerConfigBean bean = applicationContext.getBean(ConsumerConfigBean.class);
        this.classname = bean.getConsumerrefIntername();

    }
}
