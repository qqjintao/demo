package com.intertek.demo.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.monitor.entity.SystemLog;
import com.intertek.demo.monitor.service.ILogService;
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
@RequestMapping("log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @GetMapping("list")
    @RequiresPermissions("log:view")
    public ItsResponse logList(SystemLog log, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.logService.findLogs(log, request));
        return new ItsResponse().success().data(dataTable);
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("log:delete")
    @ControllerEndPoint(exceptionMessage = "删除日志失败")
    public ItsResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] logIds = ids.split(StringPool.COMMA);
        this.logService.deleteLogs(logIds);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("log:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, SystemLog lg, HttpServletResponse response) {
        List<SystemLog> logs = this.logService.findLogs(lg, request).getRecords();
        ExcelKit.$Export(SystemLog.class, response).downXlsx(logs, false);
    }
}
