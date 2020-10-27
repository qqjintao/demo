package com.intertek.demo.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:18
 */
public class ItsResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 3263740618578200457L;

    public ItsResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public ItsResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ItsResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public ItsResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public ItsResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public ItsResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
