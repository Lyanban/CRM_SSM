package com.lyanba.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: WorkbenchMainController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 10:33
 * @todo:
 */
@Controller
@RequestMapping("/workbench/main")
public class WorkbenchMainController {
    @RequestMapping("/index")
    public String toMainIndex() {
        return "/workbench/main/index";
    }
}
