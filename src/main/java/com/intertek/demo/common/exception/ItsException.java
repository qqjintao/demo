package com.intertek.demo.common.exception;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:21
 */
public class ItsException extends RuntimeException {

    private static final long serialVersionUID = -2378949933397118786L;

    public ItsException(String message){
        super(message);
    }
}
