package com.priya.fms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityDTO {

    private String activity;
    private String activityDate;
}
