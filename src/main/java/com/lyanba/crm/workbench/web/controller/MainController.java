package com.lyanba.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: MainController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 00:34
 */
@Controller
public class MainController {

    @RequestMapping("/workbench/main/index")
    public String toIndex() {
        return "/workbench/main/index";
    }
}
