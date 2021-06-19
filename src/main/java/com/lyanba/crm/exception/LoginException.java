package com.lyanba.crm.exception;

/*
    登录失败异常
 */
public class LoginException extends Exception{
    public LoginException(String message){
        super(message);
    }
}
