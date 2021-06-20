package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.DicType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: DicTypeDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:29
 * @todo:
 */
public interface DicTypeDao {
    List<DicType> getDicTypeList();

    DicType getDicTypeByCode(@Param("code") String code);

    int saveDicType(DicType dicType);

    int updateDicType(DicType dicType);

    int deleteDicType(String[] code);
}
