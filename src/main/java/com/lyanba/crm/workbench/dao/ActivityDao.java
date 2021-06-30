package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: ActivityDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:39
 * @todo:
 */
public interface ActivityDao {
    List<Activity> getAllActivity();

    // List<Activity> getActivityList(@Param("skipCount") Integer skipCount, @Param("pageSize") Integer pageSize);
    List<Activity> getActivityList(Map<String, Object> map);

    // int getActivityCount();
    int getActivityCount(Map<String, Object> map);

    Activity getActivityById(@Param("id") String id);

    int saveActivity(Activity activity);

    int updateActivity(Activity activity);

    // int deleteActivity(String[] id);
    int deleteActivity(@Param("editBy") String editBy, @Param("editTime") String editTime, @Param("ids") String[] ids);

    int saveActivityList(@Param("activityList") List<Activity> activityList);

    List<Activity> getActivityByIds(@Param("activityId") String[] activityId);
}
