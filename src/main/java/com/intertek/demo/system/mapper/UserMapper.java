package com.intertek.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intertek.demo.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:01
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByName(String username);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @param userId 权限控制
     * @return Ipage
     */
    IPage<User> findUserDetailPage(Page page, @Param("user") User user, @Param("userId") Integer userId);

    /**
     * 查找用户详细信息
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<User> findUserDetail(@Param("user") User user);
}
