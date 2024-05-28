package com.priya.fms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ACTIVITY_LOG")
public class ActivityLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    private Long activityId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "LOG")
    private String log;
    @Column(name = "CREATED_DATE")
    private String createdDate;
}
