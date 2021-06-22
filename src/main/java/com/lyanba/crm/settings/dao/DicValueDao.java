package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.DicValue;
import org.apache.ibatis.annotations.Param;

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

    DicValue getDicValueByValue(@Param("value") String value);

    int updateDicValue(DicValue dicValue);

    int deleteDicValue(String[] value);

    int deleteDicValueByTypeCode(String[] typeCode);

    List<DicValue> getDicValueByTypeCode(@Param("typeCode") String typeCode);
}
