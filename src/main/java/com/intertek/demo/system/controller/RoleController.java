package com.intertek.demo.system.controller;

import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.system.entity.Role;
import com.intertek.demo.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 17:14
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController  extends BaseController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ItsResponse getAllRoles(Role role) {
        Integer userId= ItsUtil.getCurrentUserId();
        //如果是超级管理员
        if(userId==1||userId==8){
            return new ItsResponse().success().data(roleService.findRoles(role));
        }else {
            return new ItsResponse().success().data(roleService.findUserRoles(userId,role.getRoleName()));
        }
    }



    @GetMapping("list")
    @RequiresPermissions("role:view")
    public ItsResponse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new ItsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("role:add")
    @ControllerEndPoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public ItsResponse addRole(@Valid Role role) {
        this.roleService.createRole(role);
        return new ItsResponse().success();
    }

    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    @ControllerEndPoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public ItsResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        this.roleService.deleteRoles(roleIds);
        return new ItsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("role:update")
    @ControllerEndPoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public ItsResponse updateRole(Role role) {
        this.roleService.updateRole(role);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("role:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws ItsException {
        List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
    }
}
