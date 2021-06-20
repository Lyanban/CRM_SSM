package com.lyanba.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: WorkbenchController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 10:13
 */
@Controller
@RequestMapping("/workbench")
public class WorkbenchIndexController {
    @RequestMapping("/index")
    public String toIndex() {
        return "/workbench/index";
    }
}
