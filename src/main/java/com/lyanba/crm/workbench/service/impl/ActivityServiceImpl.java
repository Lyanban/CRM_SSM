package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.workbench.dao.ActivityDao;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: ActivityServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:38
 * @todo:
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao;

    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Override
    public List<Activity> getActivityList() {
        return activityDao.getActivityList();
    }

    @Override
    public Activity getActivityById(String id) {
        return activityDao.getActivityById(id);
    }
}
