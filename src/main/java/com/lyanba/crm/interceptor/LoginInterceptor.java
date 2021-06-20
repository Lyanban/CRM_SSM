package com.lyanba.crm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: LoginInterceptor
 * @description: 登录拦截器
 * @author: LyanbA
 * @createDate: 2021/6/18 21:42
 * @todo:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">---------- 进入到用户登录拦截器 ----------<");
        System.out.println(">---------- 拦截器 preHandler ----------<");
        if (null == request.getSession(false) || null == request.getSession(false).getAttribute("user")) {
            System.out.println(">---------- 发现恶意登录，请求被拦截 ----------<");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return false;
        }
        System.out.println(">---------- 请求放行 ----------<");
        return true;
    }
}
