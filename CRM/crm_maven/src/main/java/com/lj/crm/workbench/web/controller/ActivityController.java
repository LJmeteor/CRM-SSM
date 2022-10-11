package com.lj.crm.workbench.web.controller;

import com.lj.crm.commons.constant.Constant;
import com.lj.crm.commons.domain.ReturnObject;
import com.lj.crm.commons.utils.DataUtils;
import com.lj.crm.commons.utils.HSSFUtils;
import com.lj.crm.commons.utils.UUIDUtils;
import com.lj.crm.settings.domain.User;
import com.lj.crm.settings.service.UserService;
import com.lj.crm.workbench.domain.Activity;
import com.lj.crm.workbench.domain.ActivityRemark;
import com.lj.crm.workbench.service.ActivityRemarkService;
import com.lj.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

@Controller
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        // User Service
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList",userList);
        //forward to index
        return "workbench/activity/index";

    }
    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        //二次封装
        User user=(User) session.getAttribute(Constant.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DataUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject returnObject=new ReturnObject();
        try {
            int ret=activityService.saveCreateActivity(activity);
            if (ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                returnObject.setMessage("System is busy!! please wait!");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
            returnObject.setMessage("System is busy!! please wait!");
        }
     return returnObject;

    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name, String owner, String startDate,String endDate,
                                                                int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);

        List<Activity> activityList=activityService.queryActivityByConditionForPage(map);
        int totalRows=activityService.queryCountOfActivityByCondition(map);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("activityList",activityList);
        resultMap.put("totalRows",totalRows);

        return resultMap;
    }
    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    public @ResponseBody Object deleteActivityIds(String[] id){
        // call Activity Service
        ReturnObject returnObject=new ReturnObject();
        try {
            int ret= activityService.deleteActivityByIds(id);
            if (ret >0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                returnObject.setMessage("system is busy!");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
            returnObject.setMessage("system is busy!");
        }
       return  returnObject;


    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){

        Activity activity=activityService.queryActivityById(id);
        return activity;


    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
           activity.setEditTime(DataUtils.formatDateTime(new Date()));
           User user=(User) session.getAttribute(Constant.SESSION_USER);
           activity.setEditBy(user.getId());
           ReturnObject returnObject= new ReturnObject();
           try {
               int ret=activityService.saveEditActivity(activity);
               if (ret >0){
                   returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
               }else {
                   returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                   returnObject.setMessage("System is busy ");
               }
           }catch (Exception e){
               e.printStackTrace();
               returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
               returnObject.setMessage("System is busy ");
           }
           return  returnObject;
    }

    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response) throws Exception{
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream out=response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=myStudentList.xls");
        InputStream inputStream=new FileInputStream("C:\\Users\\37199\\OneDrive\\桌面\\CRM\\studentList.xls");
        byte[] buffer=new byte[256];
        int len=0;
        while ((len=inputStream.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        inputStream.close();
        out.flush();

    }

    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public  void exportAllActivities(HttpServletResponse response)throws Exception{
        List<Activity> list=activityService.queryAllActivities();
        // create Excel file
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet("MarketActivity");
        HSSFRow row= sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("owner");
        cell=row.createCell(2);
        cell.setCellValue("name");
        cell=row.createCell(3);
        cell.setCellValue("start_date");
        cell=row.createCell(4);
        cell.setCellValue("end_date");
        cell=row.createCell(5);
        cell.setCellValue("cost");
        cell=row.createCell(6);
        cell.setCellValue("description");
        cell=row.createCell(7);
        cell.setCellValue("create_time");
        cell=row.createCell(8);
        cell.setCellValue("create_by");
        cell=row.createCell(9);
        cell.setCellValue("edit_time");
        cell=row.createCell(10);
        cell.setCellValue("edit_by");
        if (list!=null && list.size()>0) {
            Activity activity = null;
            for (int i = 0; i < list.size(); i++) {
                activity = list.get(i);
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
//        OutputStream outputStream=new FileOutputStream("C:\\Users\\37199\\OneDrive\\桌面\\CRM\\studentList.xls");
//        wb.write(outputStream);
//        outputStream.close();



        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream out=response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=myStudentList.xls");
//        InputStream inputStream =new FileInputStream("C:\\Users\\37199\\OneDrive\\桌面\\CRM\\studentList.xls");
//        byte[] buffer=new byte[256];
//        int len=0;
//        while ((len=inputStream.read(buffer))!=-1){
//            out.write(buffer,0,len);
//        }
//         inputStream.close();
        wb.write(out);
        wb.close();
         out.flush();
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session){
        User user=(User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject=new ReturnObject();
       try {
//           String  originalName=activityFile.getOriginalFilename();
//           File file=new File("C:\\Users\\37199\\OneDrive\\桌面\\Course",originalName);
//           activityFile.transferTo(file);

           HSSFWorkbook wb=new HSSFWorkbook(activityFile.getInputStream());
           HSSFSheet sheet=wb.getSheetAt(0);
           HSSFRow row=null;
           HSSFCell cell=null;
           Activity activity=null;
           List<Activity> activityList=new ArrayList<Activity>();
           for (int i=1;i<=sheet.getLastRowNum();i++) {
               row=sheet.getRow(i);
               activity= new Activity();
               activity.setId(UUIDUtils.getUUID());
               activity.setOwner(user.getId());
               activity.setCreateTime(DataUtils.formatDateTime(new Date()));
               activity.setCreateBy(user.getId()) ;
               for (int j=0;j<row.getLastCellNum();j++){
                   cell=row.getCell(j);
                   String cellValue= HSSFUtils.getCellValueForStr(cell);
                   if (j==0){
                       activity.setName(cellValue);
                   }else if (j==1){
                       activity.setStartDate(cellValue);
                   }else if (j==2){
                       activity.setEndDate(cellValue);
                   }else if (j==3){
                       activity.setCost(cellValue);
                   }else if (j==4){
                       activity.setDescription(cellValue);
                   }
               }
               activityList.add(activity);

           }
           int ret=activityService.saveCreateActivityByList(activityList);
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
           returnObject.setRetData(ret);
       }catch (Exception e){
           e.printStackTrace();
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
           returnObject.setMessage("System is busy");
       }
       return  returnObject;
    }
    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id ,HttpServletRequest request){
        Activity activity=activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarkList",activityRemarkList);
        // request forward
      return "workbench/activity/detail";
    }
}
