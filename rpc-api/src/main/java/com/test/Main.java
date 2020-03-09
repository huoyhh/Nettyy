package com.test;

import com.entity.RequestBean;
import com.entity.Student;
import com.untils.JsonUtils;

import java.lang.reflect.Method;

/**
 * @author CBeann
 * @create 2020-03-06 22:26
 */
public class Main {


    public static void main(String[] args) throws Exception {

        Student student = new Student();
        System.out.println(Student.class);

        Class<?> aClass = Class.forName("com.entity.Student");
        System.out.println(aClass);


        RequestBean requestBean = new RequestBean();
        requestBean.setRequestClass("com.service.StudentService");
        requestBean.setRequestMethod("selectStudentById");
        requestBean.setArgs(1);
        requestBean.setAtgsType(Integer.class);
        System.out.println(JsonUtils.objectToJson(requestBean));//{"requestClass":"com.service.StudentService","requestMethod":"selectStudentById","args":1}


        Integer integer=1;
        System.out.println(integer.getClass());

        Class clazz = Class.forName(requestBean.getRequestClass());
        System.out.println(clazz);


        Class atgsType = requestBean.getAtgsType();
        Method method = clazz.getMethod(requestBean.getRequestMethod(), atgsType);
        System.out.println(method);


    }
}
