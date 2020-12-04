package com.cql.commons.moudel.system;

import java.io.Serializable;

/**
 * @Author:yongbo.zhou
 * @Email:yongbo.zhou@druglots.cn
 * @Date:2019/8/13
 * @Version:1.0
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1966316490796555951L;

    /**
     * 状态码，200表示成功，非200表示各种不同的错误
     */
    private int code = 200;

    /**
     * 描述信息，成功时为"success"，错误时则是错误信息
     */
    private String message = "success";

    /**
     * 成功时返回的数据，类型为对象或数组
     * 正确格式：data: { token: 123456 }
     * 错误格式：data: 123456
     */
    private T data;

    private Result() {
    }

    /**
     * 生成错误结果对象
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     * @return 错误结果对象
     * @author yongbo.zhou
     * @date 2017/9/28
     */
    public static <T> Result<T> genErrorResult(int code, String errorMsg) {
        Result<T> result = new Result<T>();
        result.code = code;
        result.message = errorMsg;
        return result;
    }

    /**
     * 生成错误结果对象
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     * @param data     返回数据对象
     * @return 错误结果对象
     * @author yongbo.zhou
     * @date 2017/9/28
     */
    public static <T> Result<T> genErrorResult(int code, String errorMsg, T data) {
        Result<T> result = new Result<T>();
        result.code = code;
        result.message = errorMsg;
        result.data = data;
        return result;
    }

    /**
     * 生成成功结果对象
     *
     * @return 成功结果对象
     * @author yongbo.zhou
     * @date 2017/9/28
     */
    public static <T> Result<T> genSuccessResult() {
        return new Result<T>();
    }

    /**
     * 生成成功结果对象
     *
     * @param successMsg 成功信息
     * @param data       返回数据对象
     * @return 成功结果对象
     * @author yongbo.zhou
     * @date 2017/9/28
     */
    public static <T> Result<T> genSuccessResult(String successMsg, T data) {
        Result<T> result = new Result<T>();
        result.message = successMsg;
        result.data = data;
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
