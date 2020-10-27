package com.intertek.demo.monitor.service;

import com.intertek.demo.monitor.entity.ActiveUser;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:51
 */
public interface ISessionService {

    /**
     * 获取在线用户列表
     *
     * @param username 用户名
     * @return List<ActiveUser>
     */
    List<ActiveUser> list(String username);

    /**
     * 踢出用户
     *
     * @param sessionId sessionId
     */
    void forceLogout(String sessionId);
}
