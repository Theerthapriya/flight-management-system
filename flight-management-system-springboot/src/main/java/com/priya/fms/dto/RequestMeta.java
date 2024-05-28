package com.priya.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMeta {
    private Long userId;
    private String userName;
    private String emailId;
    private String userType;
}
