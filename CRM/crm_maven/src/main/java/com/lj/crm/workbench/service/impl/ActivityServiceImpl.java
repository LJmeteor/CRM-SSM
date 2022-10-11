package com.lj.crm.workbench.service.impl;

import com.lj.crm.settings.domain.User;
import com.lj.crm.workbench.domain.Activity;
import com.lj.crm.workbench.mapper.ActivityMapper;
import com.lj.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
   @Autowired
   private ActivityMapper activityMapper;

    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionsForPage(map);
    }

    public int saveCreateActivity(Activity activity) {


        return activityMapper.insertActivity(activity);
    }

    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    public List<Activity> queryAllActivities() {
        return activityMapper.queryAllActivities();
    }

    public int saveCreateActivityByList(List<Activity> activities) {
        return activityMapper.insertActivityByList(activities);
    }

    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }
}
