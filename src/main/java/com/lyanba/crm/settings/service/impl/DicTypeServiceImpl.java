package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicTypeDao;
import com.lyanba.crm.settings.dao.DicValueDao;
import com.lyanba.crm.settings.domain.DicType;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionRequiredException;
import java.util.List;

/**
 * @className: DicTypeServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 14:26
 * @todo:
 */
@Service
public class DicTypeServiceImpl implements DicTypeService {
    private DicTypeDao dicTypeDao;
    private DicValueDao dicValueDao;

    @Autowired
    public void setDicTypeDao(DicTypeDao dicTypeDao) {
        this.dicTypeDao = dicTypeDao;
    }

    @Autowired
    public void setDicValueDao(DicValueDao dicValueDao) {
        this.dicValueDao = dicValueDao;
    }

    @Override
    public List<DicType> getDicTypeList() {
        return dicTypeDao.getDicTypeList();
    }

    @Override
    public DicType getDicTypeByCode(String code) {
        return dicTypeDao.getDicTypeByCode(code);
    }

    @Override
    public void saveDicType(DicType dicType) throws TransactionRequiredException {
        if (dicTypeDao.saveDicType(dicType) != 1)
            throw new TransactionRequiredException("新增数据字典类型失败！");
    }

    @Override
    public void updateDicType(DicType dicType) throws TransactionRequiredException {
        if (dicTypeDao.updateDicType(dicType) != 1)
            throw new TransactionRequiredException("更新数据字典类型失败！");
    }

    @Transactional
    @Override
    public void deleteDicType(String[] code) throws TransactionRequiredException {
        String[] typeCode = new String[code.length];
        for (int i = 0; i < code.length; i++) {
            Object[] objects = dicValueDao.getDicValueByTypeCode(code[i]).toArray();
            DicValue v = (DicValue) objects[i];
            typeCode[i] = v.getTypeCode();
        }
        if (dicValueDao.deleteDicValueByTypeCode(typeCode) != typeCode.length)
            throw new TransactionRequiredException("删除数据字典类型失败！-1");

        if (dicTypeDao.deleteDicType(code) != code.length)
            throw new TransactionRequiredException("删除数据字典类型失败！-2");
    }
}
