package com.intertek.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intertek.demo.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 14:57
 */
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 通过用户名查找用户角色
     *
     * @param username 用户名
     * @return 用户角色集合
     */
    List<Role> findUserRole(String username);

    /**
     * 查找角色详情
     *
     * @param page 分页
     * @param role 角色
     * @return IPage<User>
     */
    IPage<Role> findRolePage(Page page, @Param("role") Role role);

    /**
     * 通过userId查找角色详情
     * @param page
     * @param role
     * @param userId
     * @return
     */
    IPage<Role> findRolePageByUserId(Page page, @Param("role") Role role, @Param("userId") Integer userId);

    /**
     * 通过名称查找用户角色列表 登录人限制
     * @param userId
     * @param roleName
     * @return
     */
    List<Role> findUserRoles(@Param("userId") Integer userId, @Param("roleName") String roleName);
}
