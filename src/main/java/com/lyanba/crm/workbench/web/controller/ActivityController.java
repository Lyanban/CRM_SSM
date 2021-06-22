package com.lyanba.crm.workbench.web.controller;

import com.lyanba.crm.utils.HandleFlag;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("/index")
    public String toIndex() {
        return "/workbench/activity/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> getActivityList() {
        List<Activity> activityList = activityService.getActivityList();
        if (null != activityList && activityList.size() > 0) {
            return HandleFlag.successObj("activityList", activityList);
        } else {
            return HandleFlag.failObj("message", "目前没有市场活动！");
        }
    }

    @RequestMapping("/detail/{id}")
    public String toDetail(Model model, @PathVariable("id") String id) {
        Activity activity = activityService.getActivityById(id);
        model.addAttribute("activity", activity);
        return "/workbench/activity/detail";
    }
}
