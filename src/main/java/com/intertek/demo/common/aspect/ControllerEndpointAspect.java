package com.intertek.demo.common.aspect;

import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.common.utils.HttpContextUtil;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.monitor.service.ILogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Aspect
@Component
public class ControllerEndpointAspect extends AspectSupport {

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.intertek.demo.common.annotation.ControllerEndPoint)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws ItsException {
        Object result;
        Method targetMethod = resolveMethod(point);
        ControllerEndPoint annotation = targetMethod.getAnnotation(ControllerEndPoint.class);
        String operation = annotation.operation();
        long start = System.currentTimeMillis();
        try {
            result = point.proceed();
            if (StringUtils.isNotBlank(operation)) {
                HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
                logService.saveLog(point, targetMethod, request, operation, start);
            }
            return result;
        } catch (Throwable throwable) {
            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            String error = ItsUtil.containChinese(message) ? exceptionMessage + "，" + message : exceptionMessage;
            throw new ItsException(error);
        }
    }
}



