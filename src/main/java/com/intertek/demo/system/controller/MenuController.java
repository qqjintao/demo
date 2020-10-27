package com.intertek.demo.system.controller;

import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.MenuTree;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.system.entity.Menu;
import com.intertek.demo.system.entity.User;
import com.intertek.demo.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 17:11
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {
    
    @Autowired
    private IMenuService menuService;

    @GetMapping("{username}")
    public ItsResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws ItsException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername())) {
            throw new ItsException("您无权获取别人的菜单");
        }
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new ItsResponse().data(userMenus);
    }

    @GetMapping("tree")
    @ControllerEndPoint(exceptionMessage = "获取菜单树失败")
    public ItsResponse getMenuTree(Menu menu) {
        MenuTree<Menu> menus = this.menuService.findMenus(menu);
        return new ItsResponse().success().data(menus.getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndPoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public ItsResponse addMenu(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return new ItsResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndPoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public ItsResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMeuns(menuIds);
        return new ItsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndPoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public ItsResponse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}
