package com.intertek.demo.system.controller;

import com.intertek.demo.common.authentication.ShiroHelper;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.resolver.MyLocaleResolver;
import com.intertek.demo.common.utils.DateUtil;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.system.entity.User;
import com.intertek.demo.system.service.IUserService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jacksy.qin
 * @date 2019/9/23 17:18
 */
@Controller("systemView")
public class ViewController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ShiroHelper shiroHelper;

    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request,Model model) {
        if (ItsUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            MyLocaleResolver m=new MyLocaleResolver();
            m.resolveLocale(request);
            model.addAttribute("lang",request.getParameter("lang"));
            mav.setViewName(ItsUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return ItsUtil.view("error/403");
    }



    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentuserAuthorizationInfo();
        User user = super.getCurrentUser();
        user.setPassword("It's a secret");
        model.addAttribute("user", userService.findByName(user.getUsername())); // 获取实时的用户信息
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles",authorizationInfo.getRoles());
        return "index";
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return ItsUtil.view("layout");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return ItsUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return ItsUtil.view("system/user/userProfile");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return ItsUtil.view("system/user/avatar");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return ItsUtil.view("system/user/profileUpdate");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return ItsUtil.view("system/user/user");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return ItsUtil.view("system/user/userAdd");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return ItsUtil.view("system/user/userDetail");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return ItsUtil.view("system/user/userUpdate");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return ItsUtil.view("system/role/role");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return ItsUtil.view("system/menu/menu");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return ItsUtil.view("system/dept/dept");
    }

    //类别管理
    @GetMapping(ItsConstant.VIEW_PREFIX + "categories/list")
    @RequiresPermissions("categories:view")
    public String Categories() {
        return ItsUtil.view("categories/categories");
    }

    @RequestMapping(ItsConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return ItsUtil.view("index");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "404")
    public String error404() {
        return ItsUtil.view("error/404");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "403")
    public String error403() {
        return ItsUtil.view("error/403");
    }

    @GetMapping(ItsConstant.VIEW_PREFIX + "500")
    public String error500() {
        return ItsUtil.view("error/500");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        model.addAttribute("user", user);
        if (transform) {
            String ssex = user.getSex();
            if (User.SEX_MALE.equals(ssex)){
                user.setSex("男");
            }else if (User.SEX_FEMALE.equals(ssex)) {
                user.setSex("女");
            }else{
                user.setSex("保密");
            }
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
}
