package com.priya.fms.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String userName;
    private String email;
    private String phoneNo;
    private String password;
}
