package com.intertek.demo.common.exception;

/**
 * Redis 连接异常
 *
 * @author jacksy.qin
 * @date 2019/9/23 16:51
 */
public class RedisConnectException extends Exception {

    private static final long serialVersionUID = 1842228699138567898L;

    public RedisConnectException(String message) {
        super(message);
    }
}
