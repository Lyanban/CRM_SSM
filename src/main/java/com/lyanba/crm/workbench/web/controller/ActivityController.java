package com.lyanba.crm.workbench.web.controller;

import com.lyanba.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: ActivityController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/20 15:34
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    private ActivityService activityService;

    @Autowired
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }
}
