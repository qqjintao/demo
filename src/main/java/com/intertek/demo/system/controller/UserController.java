package com.intertek.demo.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.common.utils.MD5Util;
import com.intertek.demo.system.entity.User;
import com.intertek.demo.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 17:16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findUserDetail(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @RequiresPermissions("user:view")
    public ItsResponse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetail(user, request));
        return new ItsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("user:add")
    @ControllerEndPoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public ItsResponse addUser(@Valid User user) {
        this.userService.createUser(user);
        return new ItsResponse().success();
    }

    @GetMapping("delete/{userIds}")
    @RequiresPermissions("user:delete")
    @ControllerEndPoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public ItsResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
        String[] ids = userIds.split(StringPool.COMMA);
        this.userService.deleteUsers(ids);
        return new ItsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("user:update")
    @ControllerEndPoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public ItsResponse updateUser(@Valid User user) {
        if (user.getUserId() == null) {
            throw new ItsException("用户ID为空");
        }
        this.userService.updateUser(user);
        return new ItsResponse().success();
    }

    @PostMapping("password/reset/{usernames}")
    @RequiresPermissions("user:password:reset")
    @ControllerEndPoint(exceptionMessage = "重置用户密码失败")
    public ItsResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) {
        String[] usernameArr = usernames.split(StringPool.COMMA);
        this.userService.resetPassword(usernameArr);
        return new ItsResponse().success();
    }

    @PostMapping("password/update")
    @ControllerEndPoint(exceptionMessage = "修改密码失败")
    public ItsResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) {
        User user = getCurrentUser();
        if (!StringUtils.equals(user.getPassword(), MD5Util.encrypt(user.getUsername(), oldPassword))) {
            throw new ItsException("原密码不正确");
        }
        userService.updatePassword(user.getUsername(), newPassword);
        return new ItsResponse().success();
    }

    @GetMapping("avatar/{image}")
    @ControllerEndPoint(exceptionMessage = "修改头像失败")
    public ItsResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) {
        User user = getCurrentUser();
        this.userService.updateAvatar(user.getUsername(), image);
        return new ItsResponse().success();
    }

    @PostMapping("theme/update")
    @ControllerEndPoint(exceptionMessage = "修改系统配置失败")
    public ItsResponse updateTheme(String theme, String isTab) {
        User user = getCurrentUser();
        this.userService.updateTheme(user.getUsername(), theme, isTab);
        return new ItsResponse().success();
    }

    @PostMapping("profile/update")
    @ControllerEndPoint(exceptionMessage = "修改个人信息失败")
    public ItsResponse updateProfile(User user) throws ItsException {
        User currentUser = getCurrentUser();
        user.setUserId(currentUser.getUserId());
        this.userService.updateProfile(user);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("user:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) {
        List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }
}
