package com.lyanba.crm.exception.resolver;

import com.lyanba.crm.exception.AjaxRequestException;
import com.lyanba.crm.exception.InterceptorException;
import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.exception.TraditionRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionResolver {
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Object loginExceptionResolver(Exception e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10001);
        map.put("message", e.getMessage());
        return map;
    }

    @ExceptionHandler(value = InterceptorException.class)
    public String interceptorExceptionResolver(Exception e) {
        System.out.println("处理没登陆");
        e.printStackTrace();
        return "redirect:/settings/user/toLogin.do";
    }

    @ExceptionHandler(value = AjaxRequestException.class)
    @ResponseBody
    public Object ajaxRequestExceptionResolver(Exception e) {
        e.printStackTrace();
        System.out.println("---------------------====================================");
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10001);
        return map;
    }

    @ExceptionHandler(value = TraditionRequestException.class)
    public String traditionRequestExceptionResolver(Exception e) {
        e.printStackTrace();
        return "redirect:/fail.jsp";
    }

    @ExceptionHandler(value = Exception.class)
    public String exceptionResolver(Exception e) {
        e.printStackTrace();
        return "redirect:/fail.jsp";
    }
}





































