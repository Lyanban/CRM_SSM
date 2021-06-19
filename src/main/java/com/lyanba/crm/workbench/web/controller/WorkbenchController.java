package com.lyanba.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: WorkbenchController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 00:26
 * @todo:
 */
@Controller
public class WorkbenchController {
    @RequestMapping("/workbench/index")
    public String toIndex() {
        return "/workbench/index";
    }
}
