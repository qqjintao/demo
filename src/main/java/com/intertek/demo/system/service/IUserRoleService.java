package com.intertek.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intertek.demo.system.entity.UserRole;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 通过角色 id 删除
     *
     * @param roleIds 角色 id
     */
    void deleteUserRolesByRoleId(List<String> roleIds);

    /**
     * 通过用户 id 删除
     *
     * @param userIds 用户 id
     */
    void deleteUserRolesByUserId(List<String> userIds);
}
