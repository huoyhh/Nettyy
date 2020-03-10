package com.annotation;

import com.condition.MyImportBeanDefinationRegister;
import com.provider.NettyServer;
import com.provider.ProviderConfigBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CBeann
 * @create 2020-03-09 22:50
 */
@ComponentScan(value = "com",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ProviderConfigBean.class, NettyServer.class})})
@Import(MyImportBeanDefinationRegister.class)
@Target(ElementType.TYPE)//方法注解
@Retention(RetentionPolicy.RUNTIME)//运行时注解
public @interface EnableConsumer {
}
