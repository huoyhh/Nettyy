package com.provider;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author CBeann
 * @create 2020-03-07 11:18
 */

@Component
@PropertySource("classpath:myrpc_provider.properties")
@Data
public class ProviderConfigBean {


    @Value("${provider.name}")
    private String providerName;

    @Value("${provider.port}")
    private Integer providerport;

    @Value("${provider.intername}")
    private String providerIntername;


    @Value("${zk.url}")
    private String zkUrl;

}
