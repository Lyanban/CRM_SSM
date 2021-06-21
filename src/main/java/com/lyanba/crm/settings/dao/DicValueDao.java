package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @className: DicValueDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:41
 * @todo:
 */
public interface DicValueDao {
    List<DicValue> getDicValueList();

    int saveDicValue(DicValue dicValue);
}
