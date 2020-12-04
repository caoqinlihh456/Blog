package com.cql.commons.exception;

public class ServiceException extends RuntimeException {

    //定义无参构造方法
    public ServiceException() {
        super();
    }

    //定义有参构造方法
    public ServiceException(String message) {
        super(message);
    }
}
