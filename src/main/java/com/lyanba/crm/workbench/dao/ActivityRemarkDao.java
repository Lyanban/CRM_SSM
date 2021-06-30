package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: ActivityRemarkDao
 * @description: ActivityRemarkDao
 * @author: LyanbA
 * @createDate: 2021/6/30 18:18
 */
public interface ActivityRemarkDao {
    List<ActivityRemark> getActivityRemarkListById(@Param("activityId") String activityId);
}
