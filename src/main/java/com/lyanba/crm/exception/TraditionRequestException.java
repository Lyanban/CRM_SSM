package com.lyanba.crm.exception;

/*
    传统请求异常
 */
public class TraditionRequestException extends Exception{
    public TraditionRequestException(){}

    public TraditionRequestException(String message){
        super(message);
    }
}
