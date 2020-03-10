package com;

import com.annotation.EnableConsumer;
import com.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author CBeann
 * @create 2020-03-09 19:58
 */
@EnableConsumer
public class Rpc_consumer_main {
    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Rpc_consumer_main.class);
        StudentService bean = (StudentService) context.getBean("studentService");
        bean.selectStudentById(123);

        System.out.println("--(rpc-consumer-main 正常启动)--");
        System.in.read();

    }
}
