package com.consumer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * @author CBeann
 * @create 2020-03-07 14:45
 */
@Component
@PropertySource("classpath:myrpc_consumer.properties")
@Data
public class ConsumerConfigBean implements PriorityOrdered {


    @Value("${consumer.name}")
    private String consumerName;

    @Value("${consumerref.intername}")
    private String consumerrefIntername;

    @Value("${zk.url}")
    private String zkUrl;


    @Override
    public int getOrder() {
        return 0;
    }
}
