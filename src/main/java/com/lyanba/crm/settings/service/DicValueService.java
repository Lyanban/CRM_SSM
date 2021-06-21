package com.lyanba.crm.settings.service;

import com.lyanba.crm.settings.domain.DicValue;

import javax.transaction.TransactionRequiredException;
import java.util.List;

/**
 * @className: DicValueService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:25
 * @todo:
 */
public interface DicValueService {
    List<DicValue> getDicValueList();

    void saveDicValue(DicValue dicValue) throws TransactionRequiredException;
}
