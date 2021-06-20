package com.lyanba.crm.settings.service;

import com.lyanba.crm.exception.AjaxRequestException;
import com.lyanba.crm.settings.domain.DicType;

import java.util.List;

/**
 * @className: DicTypeService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:25
 * @todo:
 */
public interface DicTypeService {
    List<DicType> getDicTypeList();

    DicType getDicTypeByCode(String code);

    void saveDicType(DicType dicType) throws AjaxRequestException;
}
