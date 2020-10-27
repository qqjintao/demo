package com.intertek.demo.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.utils.AddressUtil;
import com.intertek.demo.common.utils.HttpContextUtil;
import com.intertek.demo.common.utils.IPUtil;
import com.intertek.demo.common.utils.SortUtil;
import com.intertek.demo.monitor.entity.LoginLog;
import com.intertek.demo.monitor.mapper.LoginLogMapper;
import com.intertek.demo.monitor.service.ILoginLogService;
import com.intertek.demo.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:51
 */
@Service("loginLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(loginLog.getUsername())) {
            queryWrapper.lambda().eq(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda()
                    .ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                    .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }

        Page<LoginLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "loginTime", ItsConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    @Transactional
    public void deleteLoginLogs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
        return this.baseMapper.findLastSevenDaysVisitCount(user);
    }
}
