package com.intertek.demo.system.controller;

import com.intertek.demo.common.annotation.Limit;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.common.utils.CaptchaUtil;
import com.intertek.demo.common.utils.MD5Util;
import com.intertek.demo.monitor.entity.LoginLog;
import com.intertek.demo.monitor.service.ILoginLogService;
import com.intertek.demo.system.entity.User;
import com.intertek.demo.system.service.IUserService;
import com.wf.captcha.base.Captcha;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@Validated
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginLogService loginLogService;

    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public ItsResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            /*@NotBlank(message = "{required}") String verifyCode,*/
            boolean rememberMe, HttpServletRequest request) throws ItsException {
        /*if (!CaptchaUtil.verify(verifyCode, request)) {
            throw new ItsException("验证码错误！");
        }*/
        password = MD5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        super.login(token);
        // 保存登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setSystemBrowserInfo();
        this.loginLogService.saveLoginLog(loginLog);

        return new ItsResponse().success();
    }

    @PostMapping("regist")
    public ItsResponse regist(@Valid User reUser) throws ItsException {
        User user = userService.findByName(reUser.getUsername());
        if (user != null) {
            throw new ItsException("该用户名已存在");
        }
        this.userService.regist(reUser);
        return new ItsResponse().success();
    }

    @GetMapping("index/{username}")
    public ItsResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        this.userService.updateLoginTime(username);
        Map<String, Object> data = new HashMap<>();
        // 获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new ItsResponse().success().data(data);
    }

    @GetMapping("images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.outPng(110, 34, 4, Captcha.TYPE_ONLY_NUMBER, request, response);
    }
}
