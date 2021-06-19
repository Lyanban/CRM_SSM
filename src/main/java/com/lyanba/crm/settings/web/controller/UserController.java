package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.HandleFlag;
import com.lyanba.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String, Object> login(HttpServletRequest request, String loginAct, String loginPwd) {
        System.out.println(">---------- 进入到用户验证操作 ----------<");
        String ip = request.getRemoteAddr();
        System.out.println(">---------- 当前用户的 IP 地址 ----------<\n>---------- " + ip + " ----------<");
        loginPwd = MD5Util.getMD5(loginPwd);
        try {
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            return HandleFlag.success();
        } catch (LoginException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "redirect:/login.jsp";
    }
}
