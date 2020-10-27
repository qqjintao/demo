package com.intertek.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.intertek.demo.system.entity.RoleMenu;

/**
 * @author jacksy.qin
 * @date 2019/9/23 14:59
 */
public interface RoleMenuMapper  extends BaseMapper<RoleMenu> {
    /**
     * 递归删除菜单/按钮
     *
     * @param menuId menuId
     */
    void deleteRoleMenus(String menuId);
}
