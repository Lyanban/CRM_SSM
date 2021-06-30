package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.exception.TraditionRequestException;
import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.dao.ActivityDao;
import com.lyanba.crm.workbench.dao.ActivityRemarkDao;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.ActivityRemark;
import com.lyanba.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    private ActivityRemarkDao activityRemarkDao;

    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Autowired
    public void setActivityRemarkDao(ActivityRemarkDao activityRemarkDao) {
        this.activityRemarkDao = activityRemarkDao;
    }

    @Override
    public List<Activity> getActivityList() {
        return activityDao.getAllActivity();
    }

    /*@Override
    public PaginationVO<Activity> getActivityList(Integer skipCount, Integer pageSize) {
        int total = activityDao.getActivityCount();
        List<Activity> activityList = activityDao.getActivityList(skipCount, pageSize);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(activityList);
        return vo;
    }*/

    @Override
    public PaginationVO<Activity> getActivityList(Map<String, Object> map) {
        int total = activityDao.getActivityCount(map);
        List<Activity> activityList = activityDao.getActivityList(map);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(activityList);
        return vo;
    }

    @Override
    public Activity getActivityById(String id) {
        return activityDao.getActivityById(id);
    }

    @Override
    public void saveActivity(Activity activity) throws TraditionRequestException {
        if (1 != activityDao.saveActivity(activity)) {
            throw new TraditionRequestException("新增市场活动失败！");
        }
    }

    @Override
    public void updateActivity(Activity activity) throws TraditionRequestException {
        if (1 != activityDao.updateActivity(activity))
            throw new TraditionRequestException("更新市场活动失败！");
    }

    /*@Override
    public void deleteActivity(Map<String, Object> map) throws TraditionRequestException {
    }*/
    @Override
    public void deleteActivity(String editBy, String editTime, String[] ids) throws TraditionRequestException {
        if (ids.length != activityDao.deleteActivity(editBy, editTime, ids))
            throw new TraditionRequestException("删除市场活动失败！");
    }

    @Override
    public void saveActivityList(List<Activity> activityList) throws TraditionRequestException {
        if (activityList.size() != activityDao.saveActivityList(activityList))
            throw new TraditionRequestException("批量新增市场活动失败！");
    }

    @Override
    public List<Activity> getActivityByIds(String[] activityId) {
        return activityDao.getActivityByIds(activityId);
    }

    @Override
    public List<ActivityRemark> getActivityRemarkListById(String activityId) {
        return activityRemarkDao.getActivityRemarkListById(activityId);
    }
}
