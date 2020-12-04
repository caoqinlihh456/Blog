package com.cql.admin.advice;

import com.cql.commons.exception.ServiceException;
import com.cql.commons.moudel.system.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常设置
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 友好提示拦截参数异常  对ConstraintViolationException异常进行处理  //拦截异常友好提示
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInfo = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return Result.genErrorResult(-1, errorInfo.toString());
    }


    /**
     * shiro 无权限统一返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result UnauthorizedException(UnauthorizedException ex) {
        return Result.genErrorResult(-1, "您尚未该权限");
    }


    @ExceptionHandler(ServiceException.class)
    public Result ServiceException(ServiceException ex) {
        return Result.genErrorResult(-1, ex.getMessage());
    }


    /**
     * 未知异常返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception ex) {
        log.error("Exception{}:", ex.getMessage());
        return Result.genErrorResult(-1, "系统异常,请联系我");
    }


}
