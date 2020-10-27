package com.intertek.demo.common.function;

import com.intertek.demo.common.exception.RedisConnectException;

/**
 * @author jacksy.qin
 * @date 2019/9/24 15:37
 */

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
