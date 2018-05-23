package com.xxx.controller.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * <p>
 * API请求的返回结果
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
    private Integer httpStatus;
    private Integer errorCode;
    private String errorMsg;

    private BaseResponse() {}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 创建响应体，成功返回，设置返回的数据
     * 
     * @param data  
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(E data) {
        return build(HttpStatus.OK.value(), ErrorConstants.Errors.SUCCESS, data);
    }
    
    /**
     * 创建响应体，成功返回，设置返回的数据，单独设置http状态码
     * 
     * @param status HTTP状态码
     * @param data  
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(int httpStatus, E data) {
        return build(httpStatus, ErrorConstants.Errors.SUCCESS, data);
    }
    
    /**
     * 创建响应体，有错误，但http状态仍是HttpStatus.OK
     * 
     * @param error     错误信息
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(ErrorConstants.Errors error) {
        return build(HttpStatus.OK.value(), error, null);
    }

    /**
     * 创建响应体，有错误，单独设置http状态码
     * 
     * @param status    HTTP状态码
     * @param error     错误信息
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(int httpStatus, ErrorConstants.Errors error) {
        return build(httpStatus, error, null);
    }

    /**
     * 创建响应体。
     * 
     * @param status    HTTP状态码
     * @param error     错误信息
     * @param data      返回的数据
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(int httpStatus, ErrorConstants.Errors error, E data) {
        BaseResponse<E> response = new BaseResponse<>();
        response.data = data;
        response.httpStatus = httpStatus;
        response.errorCode = error.getCode();
        response.errorMsg = error.getMessage();
        return response;
    }
}
