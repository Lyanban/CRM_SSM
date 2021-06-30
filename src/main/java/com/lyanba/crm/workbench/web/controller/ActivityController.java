package com.lyanba.crm.workbench.web.controller;

import com.lyanba.crm.exception.TraditionRequestException;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.DateTimeUtil;
import com.lyanba.crm.utils.HandleFlag;
import com.lyanba.crm.utils.UUIDUtil;
import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.ActivityRemark;
import com.lyanba.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    private UserService userService;

    @Autowired
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "/workbench/activity/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> getActivityList(Integer pageNo, Integer pageSize, String name, String owner, String startDate, String endDate) {
        int skipCount = (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        // List<Activity> activityList = activityService.getActivityList();
        PaginationVO<Activity> vo = activityService.getActivityList(map);
        if (null != vo && vo.getDataList().size() > 0) {
            return HandleFlag.successObj("vo", vo);
        } else {
            return HandleFlag.failObj("message", "目前没有市场活动！");
        }
    }

    @RequestMapping("/userListAndActivity/{id}")
    @ResponseBody
    public Map<String, Object> getUserListAndActivity(@PathVariable("id") String id) {
        Activity activity = activityService.getActivityById(id);
        List<User> userList = userService.getUserList();
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("activity", activity);
        if (null != activity) {
            return HandleFlag.successObj("data", map);
        } else {
            return HandleFlag.failObj("message", "Error！");
        }
    }

    @RequestMapping("/detail/{id}")
    public String toDetail(Model model, @PathVariable("id") String id) throws TraditionRequestException {
        Activity activity = activityService.getActivityById(id);
        if (null == activity) throw new TraditionRequestException("查看市场活动异常！");
        User user = userService.getUserById(activity.getOwner());
        activity.setOwner(user.getName());
        model.addAttribute("activity", activity);
        return "/workbench/activity/detail";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> saveActivity(Activity activity) {
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        activity.setId(id);
        activity.setCreateTime(createTime);
        try {
            activityService.saveActivity(activity);
            return HandleFlag.success();
        } catch (TraditionRequestException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> updateActivity(Activity activity) {
        String editTime = DateTimeUtil.getSysTime();
        activity.setEditTime(editTime);
        try {
            activityService.updateActivity(activity);
            return HandleFlag.success();
        } catch (TraditionRequestException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteActivity(HttpSession session, String[] ids) {
        String editBy = ((User) session.getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        try {
            activityService.deleteActivity(editBy, editTime, ids);
            return HandleFlag.success();
        } catch (TraditionRequestException e) {
            e.printStackTrace();
            return HandleFlag.failObj("message", e.getMessage());
        }
    }

    @RequestMapping("/file/upload")
    public String uploadFile(@RequestParam("activityFile") MultipartFile activityFile, HttpSession session) throws TraditionRequestException, IOException {
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) session.getAttribute("user")).getName();
        String owner = ((User) session.getAttribute("user")).getId();

        String originalFilename = activityFile.getOriginalFilename();
        if (null == originalFilename) throw new TraditionRequestException("上传文件错误！");
        int index = originalFilename.lastIndexOf(".") + 1;
        String suffix = originalFilename.substring(index);
        if (suffix.isEmpty()) throw new TraditionRequestException("请上传带有后缀名的文件！");
        if (!"xls".equals(suffix)) throw new TraditionRequestException("仅能上传Excel表格文件！");

        //将原本的文件名称,进行重写,重写为唯一的文件标识,以免因为上传相同名字的文件导致被覆盖
        String newFileName = "Activity_" + DateTimeUtil.getSysTimeForUpload() + "." + suffix;
        String filePath = "/Users/lyanba/DiskD/WorkSpaces/IntelliJ IDEA/CRM_SSM/src/main/webapp/dataDir/";

        activityFile.transferTo(new File(filePath + newFileName));

        FileInputStream fileInputStream = new FileInputStream(filePath + newFileName);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);

        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<Activity> activityList = new ArrayList<>();
        for (int i = 1; i < lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            String name = row.getCell(0).getStringCellValue();
            String startDate = row.getCell(1).getStringCellValue();
            String endDate = row.getCell(2).getStringCellValue();
            String cost = row.getCell(3).getStringCellValue();
            String description = row.getCell(4).getStringCellValue();
            Activity activity = new Activity();
            activity.setId(UUIDUtil.getUUID());
            activity.setOwner(owner);
            activity.setName(name);
            activity.setStartDate(startDate);
            activity.setEndDate(endDate);
            activity.setCost(cost);
            activity.setDescription(description);
            activity.setCreateTime(createTime);
            activity.setCreateBy(createBy);
            activity.setIsDelete("0");
            activityList.add(activity);
        }
        activityService.saveActivityList(activityList);
        return "redirect:/workbench/activity/index";
    }

    @RequestMapping("/file/export/all")
    public void exportAllActivity(HttpServletResponse response) {
        List<Activity> activityList = activityService.getActivityList();
        try {
            generateExcelAndDownload(activityList, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/file/export/select")
    public void exportSelectedActivity(@RequestParam("activityId") String[] activityId, HttpServletResponse response) {
        List<Activity> activityList = activityService.getActivityByIds(activityId);
        try {
            generateExcelAndDownload(activityList, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateExcelAndDownload(List<Activity> activityList, HttpServletResponse response) throws IOException {
        if (null != activityList) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("市场活动列表");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("唯一标识");
            row.createCell(1).setCellValue("市场活动名称");
            row.createCell(2).setCellValue("所有者");
            row.createCell(3).setCellValue("开始时间");
            row.createCell(4).setCellValue("结束时间");
            row.createCell(5).setCellValue("成本");
            row.createCell(6).setCellValue("描述");
            row.createCell(7).setCellValue("创建时间");
            row.createCell(8).setCellValue("创建人");
            row.createCell(9).setCellValue("修改时间");
            row.createCell(10).setCellValue("修改人");
            row.createCell(11).setCellValue("是否删除");

            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                HSSFRow sheetRow = sheet.createRow(i + 1);
                sheetRow.createCell(0).setCellValue(activity.getId());
                sheetRow.createCell(1).setCellValue(activity.getName());
                sheetRow.createCell(2).setCellValue(activity.getOwner());
                sheetRow.createCell(3).setCellValue(activity.getStartDate());
                sheetRow.createCell(4).setCellValue(activity.getEndDate());
                sheetRow.createCell(5).setCellValue(activity.getCost());
                sheetRow.createCell(6).setCellValue(activity.getDescription());
                sheetRow.createCell(7).setCellValue(activity.getCreateTime());
                sheetRow.createCell(8).setCellValue(activity.getCreateBy());
                sheetRow.createCell(9).setCellValue(activity.getEditTime());
                sheetRow.createCell(10).setCellValue(activity.getEditBy());
                sheetRow.createCell(11).setCellValue("0".equals(activity.getIsDelete()) ? "否" : "是");
            }
            response.setContentType("octets/stream");
            response.setHeader("Content-Disposition", "attachment;filename=Activity-" + DateTimeUtil.getSysTime() + ".xls");
            workbook.write(response.getOutputStream());
        }
    }

    @RequestMapping("/remark/list/{activityId}")
    @ResponseBody
    public Map<String, Object> getActivityRemarkListById(@PathVariable("activityId") String activityId) {
        List<ActivityRemark> activityRemarkList = activityService.getActivityRemarkListById(activityId);
        if (null == activityRemarkList || 0 == activityRemarkList.size()) {
            return HandleFlag.failObj("message", "没有关联数据");
        }
        return HandleFlag.successObj("activityRemarkList", activityRemarkList);
    }
}
