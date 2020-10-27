package com.intertek.demo.common.exception;

/**
 * 限流异常
 *
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
public class LimitAccessException extends Exception {

    private static final long serialVersionUID = -2951710152286494189L;

    public LimitAccessException(String message) {
        super(message);
    }
}