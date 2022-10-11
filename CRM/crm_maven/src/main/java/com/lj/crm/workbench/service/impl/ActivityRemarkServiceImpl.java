package com.lj.crm.workbench.service.impl;

import com.lj.crm.workbench.domain.ActivityRemark;
import com.lj.crm.workbench.mapper.ActivityRemarkMapper;
import com.lj.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkForDetailById(activityId );
    }
}
