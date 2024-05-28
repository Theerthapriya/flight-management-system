package com.priya.fms.service;

import com.priya.fms.dto.ActivityDTO;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> getActivityLogs(String userName);
}
