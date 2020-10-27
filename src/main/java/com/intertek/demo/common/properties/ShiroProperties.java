package com.intertek.demo.common.properties;

import lombok.Data;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Data
public class ShiroProperties {

    private long sessionTimeout;
    private int cookieTimeout;
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
    private String unauthorizedUrl;
}
