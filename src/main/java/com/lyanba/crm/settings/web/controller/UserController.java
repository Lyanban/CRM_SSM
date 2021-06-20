package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/19 21:10
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, String flag, String loginAct, String loginPwd) {
        System.out.println(">---------- 进入到用户验证操作 ----------<");
        String ip = request.getRemoteAddr();
        System.out.println(">---------- 当前用户的 IP 地址 ----------<\n>---------- " + ip + " ----------<");
        loginPwd = MD5Util.getMD5(loginPwd);
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getUserByLoginActAndLoginPwd(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            if ("a".equals(flag)) {
                Cookie loginActCookie = new Cookie("loginAct", loginAct);
                Cookie loginPwdCookie = new Cookie("loginPwd", loginPwd);

                loginActCookie.setPath("/");
                loginPwdCookie.setPath("/");

                loginActCookie.setMaxAge(60 * 60 * 24 * 10);
                loginPwdCookie.setMaxAge(60 * 60 * 24 * 10);

                response.addCookie(loginActCookie);
                response.addCookie(loginPwdCookie);
            }
            result.put("success", 10000);
            return result;
        } catch (LoginException e) {
            e.printStackTrace();
            result.put("success", 10001);
            result.put("message", e.getMessage());
            return result;
        }
    }

    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request) throws LoginException {
        //十天免登陆操作,自动登录
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;
        if (cookies != null && cookies.length > 0) {
            //获取登录的用户名和密码
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("loginAct".equals(name)) {
                    //获取到了用户名
                    loginAct = cookie.getValue();
                    continue;
                }
                if ("loginPwd".equals(name)) {
                    //获取到了密码(加密后)
                    loginPwd = cookie.getValue();
                }
            }
            String ip = request.getRemoteAddr();
            if (loginAct != null && loginPwd != null && loginAct.length() > 0 && loginPwd.length() > 0) {
                //调用service的登录方法,进行自动登录操作
                User user = userService.getUserByLoginActAndLoginPwd(loginAct, loginPwd, ip);
                if (user != null) {
                    //重新存入到session
                    request.getSession().setAttribute("user", user);
                    return "redirect:/workbench/index";
                }
            }
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //销毁session中的数据
        request.getSession().removeAttribute("user");
        //清空cookie中的用户名
        //cookie对象并没有删除的操作
        //我们需要将用户名和密码,设置为空字符串即可
        //通过response对象写入回浏览器中,并设置过期时间为0,代表立即过期
        //        for (Cookie cookie : request.getCookies()) {
        //        }
        Cookie loginActCookie = new Cookie("loginAct", "");
        Cookie loginPwdCookie = new Cookie("loginPwd", "");

        loginActCookie.setPath("/");
        loginPwdCookie.setPath("/");

        loginActCookie.setMaxAge(0);
        loginPwdCookie.setMaxAge(0);

        //浏览器会将原有的用户名和密码的session数据进行覆盖
        response.addCookie(loginActCookie);
        response.addCookie(loginPwdCookie);

        //重定向到登录页面
        return "redirect:/settings/user/toLogin.do";
    }
}
