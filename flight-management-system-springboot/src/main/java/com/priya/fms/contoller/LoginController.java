package com.priya.fms.contoller;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.LoginRequestDTO;
import com.priya.fms.dto.SignUpRequestDTO;
import com.priya.fms.service.LoginService;
import com.priya.fms.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = loginService.signUp(signUpRequestDTO);
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        APIResponse apiResponse = loginService.login(loginRequestDTO);
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
