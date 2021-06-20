package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicTypeDao;
import com.lyanba.crm.settings.domain.DicType;
import com.lyanba.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public void setDicTypeDao(DicTypeDao dicTypeDao) {
        this.dicTypeDao = dicTypeDao;
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

    @Override
    public void deleteDicType(String[] code) throws TransactionRequiredException {
        if (dicTypeDao.deleteDicType(code) != code.length)
            throw new TransactionRequiredException("删除数据字典类型失败！");
    }
}
