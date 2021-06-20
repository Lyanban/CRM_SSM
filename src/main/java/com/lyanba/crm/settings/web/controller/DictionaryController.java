package com.lyanba.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: DictionaryController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 11:28
 * @todo:
 */
@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {
    @RequestMapping("/index")
    public String toIndex() {
        return "/settings/dictionary/index";
    }

    @RequestMapping("/type/index")
    public String toTypeIndex() {
        return "/settings/dictionary/type/index";
    }

    @RequestMapping("/value/index")
    public String toValueIndex() {
        return "/settings/dictionary/value/index";
    }
}
