package com.condition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author CBeann
 * @create 2020-03-09 19:36
 */
//@Component
//@PropertySource(value = "classpath:myrpc_consumer.properties")
public class MyImportBeanDefinationRegister implements ImportBeanDefinitionRegistrar {

    @Value("${provider.intername}")
    private String consumerrefintername;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean consumerConfigBean = registry.containsBeanDefinition("consumerConfigBean");

        //如果有消费者配置Bean，说明是消费者
        if (consumerConfigBean) {
            try {
//                BeanDefinition beanDefinition = new RootBeanDefinition();
//                System.out.println("----------------");
//                System.out.println(consumerrefintername);
//                String[] split = consumerrefintername.split(".");
//                //设置名称
//                beanDefinition.setBeanClassName(split[split.length - 1]);
//                //设置类型
//                ((RootBeanDefinition) beanDefinition).setBeanClass(RefRpcBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
