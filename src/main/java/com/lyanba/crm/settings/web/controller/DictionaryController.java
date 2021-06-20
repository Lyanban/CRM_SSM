package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.exception.AjaxRequestException;
import com.lyanba.crm.settings.domain.DicType;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicTypeService;
import com.lyanba.crm.settings.service.DicValueService;
import com.lyanba.crm.utils.HandleFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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
    private DicTypeService dicTypeService;
    private DicValueService dicValueService;

    @Autowired
    public void setDicTypeService(DicTypeService dicTypeService) {
        this.dicTypeService = dicTypeService;
    }

    @Autowired
    public void setDicValueService(DicValueService dicValueService) {
        this.dicValueService = dicValueService;
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "/settings/dictionary/index";
    }

    @RequestMapping("/type/index")
    public String toTypeIndex(Model model) {
        List<DicType> dicTypeList = dicTypeService.getDicTypeList();
        model.addAttribute("dicTypeList", dicTypeList);
        return "/settings/dictionary/type/index";
    }

    @RequestMapping("/type/toSave")
    public String toTypeSave() {
        return "/settings/dictionary/type/save";
    }

    @RequestMapping("/type/getDicTypeByCode")
    @ResponseBody
    public Map<String, Object> getDicTypeByCode(String code) {
        DicType dicType = dicTypeService.getDicTypeByCode(code);
        return null == dicType ? HandleFlag.successObj("message", "√") : HandleFlag.failObj("message", "该字典类型已存在！");
    }

    @PostMapping("/type/save")
    @ResponseBody
    public Map<String, Object> saveDicType(DicType dicType) {
        try {
            dicTypeService.saveDicType(dicType);
            return HandleFlag.success();
        } catch (AjaxRequestException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/type/toEdit")
    public String toTypeEdit() {
        return "/settings/dictionary/type/edit";
    }

    @RequestMapping("/value/index")
    public String toValueIndex(Model model) {
        List<DicValue> dicValueList = dicValueService.getDicValueList();
        model.addAttribute("dicValueList", dicValueList);
        return "/settings/dictionary/value/index";
    }

    @RequestMapping("/value/toSave")
    public String toValueSave() {
        return "/settings/dictionary/value/save";
    }

    @RequestMapping("/value/toEdit")
    public String toValueEdit() {
        return "/settings/dictionary/value/edit";
    }
}
