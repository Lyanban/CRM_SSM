package com.lyanba.crm.workbench.service;

import com.lyanba.crm.exception.TraditionRequestException;
import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @className: ActivityService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:38
 * @todo:
 */
public interface ActivityService {
    List<Activity> getActivityList();

    // PaginationVO<Activity> getActivityList(Integer skipCount, Integer pageSize);

    PaginationVO<Activity> getActivityList(Map<String, Object> map);

    Activity getActivityById(String id);

    void saveActivity(Activity activity) throws TraditionRequestException;

    void updateActivity(Activity activity) throws TraditionRequestException;

    // void deleteActivity(Map<String, Object> map) throws TraditionRequestException;
    void deleteActivity(String editBy, String editTime, String[] ids) throws TraditionRequestException;

    void saveActivityList(List<Activity> activityList) throws TraditionRequestException;

    List<Activity> getActivityByIds(String[] activityId);

    List<ActivityRemark> getActivityRemarkListById(String activityId);
}
