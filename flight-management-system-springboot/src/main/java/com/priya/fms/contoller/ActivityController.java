package com.priya.fms.contoller;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.ActivityDTO;
import com.priya.fms.dto.LoginRequestDTO;
import com.priya.fms.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @GetMapping("/activityLogs/{userName}")
    public ResponseEntity<List<ActivityDTO>> login(@RequestParam String userName) {

        List<ActivityDTO> activityDTOList= activityService.getActivityLogs(userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(activityDTOList);
    }
}
