package com.intertek.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.intertek.demo.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 14:50
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查找用户权限集
     *
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 查找用户菜单集合
     *
     * @param username 用户名
     * @return 用户菜单集合
     */
    List<Menu> findUserMenus(String username);

    List<Menu> findMenuList(@Param("userId") Integer userId);
}
