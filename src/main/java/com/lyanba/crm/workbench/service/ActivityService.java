package com.lyanba.crm.workbench.service;

import com.lyanba.crm.workbench.domain.Activity;

import java.util.List;

/**
 * @className: ActivityService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:38
 * @todo:
 */
public interface ActivityService {
    List<Activity> getActivityList();

    Activity getActivityById(String id);
}
