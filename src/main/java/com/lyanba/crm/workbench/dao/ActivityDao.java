package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: ActivityDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:39
 * @todo:
 */
public interface ActivityDao {
    List<Activity> getActivityList();

    Activity getActivityById(@Param("id") String id);
}
