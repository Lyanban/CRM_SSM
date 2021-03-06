package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.settings.domain.DicType;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicTypeService;
import com.lyanba.crm.settings.service.DicValueService;
import com.lyanba.crm.utils.HandleFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.TransactionRequiredException;
import java.util.List;
import java.util.Map;

/**
 * @className: DictionaryController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 11:28
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
        return null == dicType ? HandleFlag.successObj("message", "???") : HandleFlag.failObj("message", "???????????????????????????");
    }

    @RequestMapping("/type/list")
    @ResponseBody
    public List<DicType> getDicTypeList() {
        List<DicType> dicTypeList = dicTypeService.getDicTypeList();
        return dicTypeList;
    }

    @PostMapping("/type/save")
    @ResponseBody
    public Map<String, Object> saveDicType(DicType dicType) {

        try {
            dicTypeService.saveDicType(dicType);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/type/toEdit/{code}")
    public String toTypeEdit(Model model, @PathVariable("code") String code) {
        DicType dicType = dicTypeService.getDicTypeByCode(code);
        model.addAttribute("dicType", dicType);
        return "/settings/dictionary/type/edit";
    }

    @RequestMapping("/type/update")
    @ResponseBody
    public Map<String, Object> updateDicType(DicType dicType) {
        try {
            dicTypeService.updateDicType(dicType);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/type/delete")
    @ResponseBody
    public Map<String, Object> deleteDicType(String[] code) {
        try {
            dicTypeService.deleteDicType(code);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/value/index")
    public String toValueIndex() {
        return "/settings/dictionary/value/index";
    }

    @RequestMapping("/value/list")
    @ResponseBody
    public List<DicValue> getDicValueList() {
        List<DicValue> dicValueList = dicValueService.getDicValueList();
        return dicValueList;
    }

    @RequestMapping("/value/toSave")
    public String toValueSave() {
        return "/settings/dictionary/value/save";
    }

    @RequestMapping("/value/save")
    @ResponseBody
    public Map<String, Object> saveDicValue(DicValue dicValue) {
        try {
            dicValueService.saveDicValue(dicValue);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/value/toEdit/{value}")
    public String toValueEdit(Model model, @PathVariable("value") String value) {
        DicValue dicValue = dicValueService.getDicValueByValue(value);
        model.addAttribute("dicValue", dicValue);
        return "/settings/dictionary/value/edit";
    }

    @RequestMapping("/value/getDicValueByValue")
    @ResponseBody
    public Map<String, Object> getDicValueByValue(String value) {
        DicValue dicValue = dicValueService.getDicValueByValue(value);
        return null == dicValue ? HandleFlag.successObj("message", "???") : HandleFlag.failObj("message", "????????????????????????");
    }

    @RequestMapping("/value/update")
    @ResponseBody
    public Map<String, Object> updateDicValue(DicValue dicValue) {
        try {
            dicValueService.updateDicValue(dicValue);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/value/delete")
    @ResponseBody
    public Map<String, Object> deleteDicValue(String[] value) {
        try {
            dicValueService.deleteDicValue(value);
            return HandleFlag.success();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }
}
