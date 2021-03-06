package com.intertek.demo.common.handler;

import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.exception.FileDownloadException;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.common.exception.LimitAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ItsResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new ItsResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("系统内部异常");
    }

    @ExceptionHandler(value = ItsException.class)
    public ItsResponse handleItsException(ItsException e) {
        log.error("系统错误", e);
        return new ItsResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ItsResponse
     */
    @ExceptionHandler(BindException.class)
    public ItsResponse validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new ItsResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return ItsResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ItsResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new ItsResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    @ExceptionHandler(value = LimitAccessException.class)
    public ItsResponse handleLimitAccessException(LimitAccessException e) {
        log.debug("LimitAccessException", e);
        return new ItsResponse().code(HttpStatus.TOO_MANY_REQUESTS).message(e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ItsResponse handleUnauthorizedException(UnauthorizedException e) {
        log.debug("UnauthorizedException", e);
        return new ItsResponse().code(HttpStatus.FORBIDDEN).message(e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ItsResponse handleAuthenticationException(AuthenticationException e) {
        log.debug("AuthenticationException", e);
        return new ItsResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public ItsResponse handleExpiredSessionException(ExpiredSessionException e) {
        log.debug("ExpiredSessionException", e);
        return new ItsResponse().code(HttpStatus.UNAUTHORIZED).message(e.getMessage());
    }

    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFileDownloadException(FileDownloadException e) {
        log.error("FileDownloadException", e);
    }
}
