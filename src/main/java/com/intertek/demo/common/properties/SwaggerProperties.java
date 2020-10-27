package com.intertek.demo.common.properties;

import lombok.Data;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Data
public class SwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;
}
