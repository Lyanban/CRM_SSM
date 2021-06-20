package com.lyanba.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: SettingsIndexController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 11:18
 * @todo:
 */
@Controller
@RequestMapping("/settings/")
public class SettingsIndexController {
    @RequestMapping("/index")
    public String toIndex() {
        return "/settings/index";
    }
}
