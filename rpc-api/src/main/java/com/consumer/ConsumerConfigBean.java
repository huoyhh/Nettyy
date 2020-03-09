package com.consumer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author CBeann
 * @create 2020-03-07 14:45
 */
@Component
@PropertySource("classpath:myrpc_consumer.properties")
@Data
public class ConsumerConfigBean {


    @Value("${consumer.name}")
    private String consumerName;

    @Value("${consumerref.intername}")
    private String consumerrefIntername;

    @Value("${zk.url}")
    private String zkUrl;
}
