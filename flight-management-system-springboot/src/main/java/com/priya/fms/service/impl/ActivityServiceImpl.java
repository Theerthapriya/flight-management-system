package com.priya.fms.service.impl;

import com.priya.fms.dto.ActivityDTO;
import com.priya.fms.entity.ActivityLogsEntity;
import com.priya.fms.repo.ActivityRepository;
import com.priya.fms.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public List<ActivityDTO> getActivityLogs(String userName) {
        List<ActivityDTO> activityDTOList = new ArrayList<>();
        List<ActivityLogsEntity> activityLogsEntityList = activityRepository.findAllByUserName(userName);
        for (ActivityLogsEntity activity : activityLogsEntityList) {
            activityDTOList.add(ActivityDTO.builder()
                    .activity(activity.getLog())
                    .activityDate(activity.getCreatedDate()).build());
        }
        return activityDTOList;
    }
}
