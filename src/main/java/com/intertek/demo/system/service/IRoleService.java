package com.intertek.demo.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.system.entity.Role;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
public interface IRoleService extends IService<Role> {


    /**
     * 通过用户名查找用户角色
     *
     * @param username 用户名
     * @return 用户角色集合
     */
    List<Role> findUserRole(String username);

    /**
     * 查找所有角色
     *
     * @param role 角色对象（用于传递查询条件）
     * @return 角色集合
     */
    List<Role> findRoles(Role role);

    /**
     * 查找所有角色（分页）
     *
     * @param role    角色对象（用于传递查询条件）
     * @param request request
     * @return IPage
     */
    IPage<Role> findRoles(Role role, QueryRequest request);

    /**
     * 通过角色名称查找相应角色
     *
     * @param roleName 角色名称
     * @return 角色
     */
    Role findByName(String roleName);

    /**
     * 新增角色
     *
     * @param role 待新增的角色
     */
    void createRole(Role role);

    /**
     * 修改角色
     *
     * @param role 待修改的角色
     */
    void updateRole(Role role);


    /**
     * 删除角色
     *
     * @param roleIds 待删除角色的 id
     */
    void deleteRoles(String roleIds);

    /**
     * 通过名称查找用户角色列表 登录人限制
     * @param userId
     * @param roleName
     * @return
     */
    List<Role> findUserRoles(Integer userId, String roleName);
}
