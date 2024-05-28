package com.priya.fms.service;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.LoginRequestDTO;
import com.priya.fms.dto.SignUpRequestDTO;

public interface LoginService {
    APIResponse signUp(SignUpRequestDTO signUpRequestDTO);
    public APIResponse login(LoginRequestDTO loginRequestDTO);

}

