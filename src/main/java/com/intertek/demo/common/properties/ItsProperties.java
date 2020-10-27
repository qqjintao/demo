package com.intertek.demo.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:its.properties"})
@ConfigurationProperties(prefix = "its")
public class ItsProperties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean autoOpenBrowser = true;
    private String[] autoOpenBrowserEnv = {};
    private SwaggerProperties swagger = new SwaggerProperties();
}
