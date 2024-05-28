package com.priya.fms.service.impl;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.LoginRequestDTO;
import com.priya.fms.dto.SignUpRequestDTO;
import com.priya.fms.entity.UserEntity;
import com.priya.fms.repo.UserRepository;
import com.priya.fms.service.LoginService;
import com.priya.fms.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public APIResponse signUp(SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = new APIResponse();
        List<UserEntity> entity = userRepository.findAllByEmail(signUpRequestDTO.getEmail());
        if(entity != null && !entity.isEmpty()){
            apiResponse.setError("Email Exists ");
            apiResponse.setStatus(500);            
            return apiResponse;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequestDTO.getUserName());
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setPhoneNumber(signUpRequestDTO.getPhoneNo());
        userEntity.setPassword(signUpRequestDTO.getPassword());
        userEntity.setRole("user");

        userEntity = userRepository.save(userEntity);

        String token = jwtUtils.generateJwt(userEntity);

        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse login(LoginRequestDTO loginRequestDTO) {

        APIResponse apiResponse = new APIResponse();
        UserEntity user = userRepository.findByEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if(user == null){
            apiResponse.setError("login failed please check UserName/Password");
            apiResponse.setStatus(500);            
            return apiResponse;
        }

        String token = jwtUtils.generateJwt(user);
        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);
        data.put("username", user.getName());
        data.put("email", user.getEmail());
        data.put("role", user.getRole());
        data.put("userId", user.getUserId());
        apiResponse.setData(data);
        return apiResponse;
    }
}
