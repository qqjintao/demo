package com.intertek.demo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intertek.demo.common.authentication.ShiroRealm;
import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.common.utils.MD5Util;
import com.intertek.demo.common.utils.SortUtil;
import com.intertek.demo.system.entity.User;
import com.intertek.demo.system.entity.UserRole;
import com.intertek.demo.system.mapper.UserMapper;
import com.intertek.demo.system.service.IUserRoleService;
import com.intertek.demo.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private ShiroRealm shiroRealm;

    @Override
    public User findByName(String username) {
        return this.baseMapper.findByName(username);
    }

    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        Integer userId=ItsUtil.getCurrentUserId();
        if(userId==1||userId==8){
            Page<User> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "userId", ItsConstant.ORDER_ASC, false);
            return this.baseMapper.findUserDetailPage(page, user,null);
        }else{
            Page<User> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "userId", ItsConstant.ORDER_ASC, false);
            return this.baseMapper.findUserDetailPage(page, user, userId);
        }
    }

    @Override
    public User findUserDetail(String username) {
        User param = new User();
        param.setUsername(username);
        List<User> users = this.baseMapper.findUserDetail(param);
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    @Transactional
    public void updateLoginTime(String username) {
        User user = new User();
        user.setLastLoginTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void createUser(User user) {
        user.setCreateTime(new Date());
        user.setCreateId(ItsUtil.getCurrentUserId());
        user.setStatus(User.STATUS_VALID);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_CLOSE);
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        // 删除用户
        this.removeByIds(list);
        // 删除关联角色
        this.userRoleService.deleteUserRolesByUserId(list);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        user.setModifyId(ItsUtil.getCurrentUserId());
        updateById(user);
        // 更新关联角色
        this.userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        User currentUser = ItsUtil.getCurrentUser();
        if (StringUtils.equalsIgnoreCase(currentUser.getUsername(), user.getUsername())) {
            shiroRealm.clearCache();
        }
    }

    @Override
    @Transactional
    public void resetPassword(String[] usernames) {
        Arrays.stream(usernames).forEach(username -> {
            User user = new User();
            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));
            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        });
    }

    @Override
    @Transactional
    public void regist(String username, String password) {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setCreateId(ItsUtil.getCurrentUserId());
        user.setStatus(User.STATUS_VALID);
        user.setSex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_CLOSE);
        user.setDescription("注册用户");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L); // 注册用户角色 ID
        this.userRoleService.save(ur);
    }

    @Override
    @Transactional
    public void regist(User reUser){
        User user = new User();
        user.setPassword(MD5Util.encrypt(reUser.getUsername(), reUser.getPassword()));
        user.setUsername(reUser.getUsername());
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_CLOSE);
        user.setMobile(reUser.getMobile());
        user.setEmail(reUser.getEmail());
        user.setCompanyNameZh(reUser.getCompanyNameZh());
        user.setCompanyNameEn(reUser.getCompanyNameEn());
        user.setCompanyAddress(reUser.getCompanyAddress());
        user.setDescription("注册用户");
        user.setDeptId(reUser.getDeptId());
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L); // 注册用户角色 ID
        this.userRoleService.save(ur);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setModifyTime(new Date());
        user.setModifyId(ItsUtil.getCurrentUserId());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) {
        User user = new User();
        user.setAvatar(avatar);
        user.setModifyTime(new Date());
        user.setModifyId(ItsUtil.getCurrentUserId());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateTheme(String username, String theme, String isTab) {
        User user = new User();
        user.setTheme(theme);
        user.setIsTab(isTab);
        user.setModifyTime(new Date());
        user.setModifyId(ItsUtil.getCurrentUserId());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateProfile(User user) {
        user.setUsername(null);
        user.setRoleId(null);
        user.setPassword(null);
        updateById(user);
    }

    private void setUserRoles(User user, String[] roles) {
        List<UserRole> userRoles = new ArrayList<>();
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            userRoles.add(ur);
        });
        userRoleService.saveBatch(userRoles);
    }
}
