package com.intertek.demo.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.monitor.entity.LoginLog;
import com.intertek.demo.monitor.service.ILoginLogService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@Slf4j
@RestController
@RequestMapping("loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping("list")
    @RequiresPermissions("loginlog:view")
    public ItsResponse loginLogList(LoginLog loginLog, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return new ItsResponse().success().data(dataTable);
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("loginlog:delete")
    @ControllerEndPoint(exceptionMessage = "删除日志失败")
    public ItsResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] loginLogIds = ids.split(StringPool.COMMA);
        this.loginLogService.deleteLoginLogs(loginLogIds);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("loginlog:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, LoginLog loginLog, HttpServletResponse response) {
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog, request).getRecords();
        ExcelKit.$Export(LoginLog.class, response).downXlsx(loginLogs, false);
    }
}
