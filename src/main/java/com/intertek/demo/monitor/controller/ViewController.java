package com.intertek.demo.monitor.controller;

import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.exception.RedisConnectException;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.monitor.endpoint.FebsMetricsEndpoint.FebsMetricResponse;
import com.intertek.demo.monitor.entity.JvmInfo;
import com.intertek.demo.monitor.entity.RedisInfo;
import com.intertek.demo.monitor.entity.ServerInfo;
import com.intertek.demo.monitor.entity.TomcatInfo;
import com.intertek.demo.monitor.helper.FebsActuatorHelper;
import com.intertek.demo.monitor.service.IRedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@Controller("monitorView")
@RequestMapping(ItsConstant.VIEW_PREFIX + "monitor")
public class ViewController {

    @Autowired
    private FebsActuatorHelper actuatorHelper;
    @Autowired
    private IRedisService redisService;

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public String online() {
        return ItsUtil.view("monitor/online");
    }

    @GetMapping("log")
    @RequiresPermissions("log:view")
    public String log() {
        return ItsUtil.view("monitor/log");
    }

    @GetMapping("loginlog")
    @RequiresPermissions("loginlog:view")
    public String loginLog() {
        return ItsUtil.view("monitor/loginLog");
    }

    @GetMapping("redis/info")
    @RequiresPermissions("redis:view")
    public String getRedisInfo(Model model) throws RedisConnectException {
        List<RedisInfo> infoList = this.redisService.getRedisInfo();
        model.addAttribute("infoList", infoList);
        return ItsUtil.view("monitor/redisInfo");
    }

    @GetMapping("redis/terminal")
    @RequiresPermissions("redis:terminal:view")
    public String redisTerminal(Model model) {
        String osName = System.getProperty("os.name");
        model.addAttribute("osName", osName);
        return ItsUtil.view("monitor/redisTerminal");
    }

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public String httptrace() {
        return ItsUtil.view("monitor/httpTrace");
    }

    @GetMapping("jvm")
    @RequiresPermissions("jvm:view")
    public String jvmInfo(Model model) {
        List<FebsMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return ItsUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<FebsMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return ItsUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<FebsMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<FebsMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<FebsMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return ItsUtil.view("monitor/serverInfo");
    }

    @GetMapping("swagger")
    public String swagger() {
        return ItsUtil.view("monitor/swagger");
    }
}
