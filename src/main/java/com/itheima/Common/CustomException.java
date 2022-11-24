package com.itheima.Common;

/**
 *
 * 自定义异常类
 * 全局异常处理器中进行了添加
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
