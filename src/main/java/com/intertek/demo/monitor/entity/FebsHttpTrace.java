package com.intertek.demo.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.net.URI;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@Data
public class FebsHttpTrace implements Serializable {

    private static final long serialVersionUID = 8286382834121710757L;

    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求 url
     */
    private URI url;
    /**
     * 响应状态
     */
    private int status;
    /**
     * 耗时
     */
    private Long timeTaken;

}
