package com.lj.crm.workbench.service;

import com.lj.crm.settings.domain.User;
import com.lj.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {


    int saveCreateActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    public  int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);

    List<Activity> queryAllActivities();

    int saveCreateActivityByList(List<Activity> activities);

    Activity queryActivityForDetailById(String id);
}
