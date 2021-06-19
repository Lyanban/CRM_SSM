package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public @ResponseBody
    Map<String, Object> login(HttpSession session, String loginAct, String loginPwd) {
        loginPwd = MD5Util.getMD5(loginPwd);
        User user = userService.getUserByLoginActAndLoginPwd(loginAct, loginPwd);
        Map<String, Object> result = new HashMap<>();
        if (null != user) {
            result.put("success", 10000);
            result.put("message", "登录成功");
            // session.setAttribute("user", user);
            return result;
        } else {
            result.put("success", 10001);
            result.put("message", "登录失败");
            return result;
        }
    }

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "redirect:/login.jsp";
    }
}
