package com;

import com.condition.MyImportBeanDefinationRegister;
import com.provider.NettyServer;
import com.provider.ProviderConfigBean;
import com.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author CBeann
 * @create 2020-03-09 19:58
 */
@ComponentScan(value = "com",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ProviderConfigBean.class, NettyServer.class})})
@Import(MyImportBeanDefinationRegister.class)
public class Rpc_consumer_main {

    public static void main(String[] args) throws Exception{




        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Rpc_consumer_main.class);

//        OrederServiceImpl bean = context.getBean(OrederServiceImpl.class);
//        bean.getStudent(1);

        StudentService bean = (StudentService)context.getBean("studentService");

        bean.selectStudentById(123);

//        NettyClient bean = context.getBean(NettyClient.class);
//        System.out.println(bean);

        //context.close();

        System.out.println("-----OVER-------");
        System.in.read();



    }
}
