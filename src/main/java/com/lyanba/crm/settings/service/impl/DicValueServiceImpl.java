package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicValueDao;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
