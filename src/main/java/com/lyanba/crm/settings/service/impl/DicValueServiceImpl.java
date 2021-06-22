package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicValueDao;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicValueService;
import com.lyanba.crm.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionRequiredException;
import java.util.List;

/**
 * @className: DicValueServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:26
 * @todo:
 */
@Service
public class DicValueServiceImpl implements DicValueService {
    private DicValueDao dicValueDao;

    @Autowired
    public void setDicValueDao(DicValueDao dicValueDao) {
        this.dicValueDao = dicValueDao;
    }

    @Override
    public List<DicValue> getDicValueList() {
        return dicValueDao.getDicValueList();
    }

    @Override
    public void saveDicValue(DicValue dicValue) throws TransactionRequiredException {
        dicValue.setId(UUIDUtil.getUUID());
        if (dicValueDao.saveDicValue(dicValue) != 1)
            throw new TransactionRequiredException("新增字典值失败！");
    }

    @Override
    public DicValue getDicValueByValue(String value) {
        return dicValueDao.getDicValueByValue(value);
    }

    @Override
    public void updateDicValue(DicValue dicValue) throws TransactionRequiredException {
        if (1 != dicValueDao.updateDicValue(dicValue))
            throw new TransactionRequiredException("更新字典值失败!");
    }

    @Override
    public void deleteDicValue(String[] value) throws TransactionRequiredException {
        if (dicValueDao.deleteDicValue(value) != value.length)
            throw new TransactionRequiredException("删除字典值失败!");
    }
}
