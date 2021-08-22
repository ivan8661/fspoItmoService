package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Entities.ScheduleUser;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Services.ScheduleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ScheduleUserController {

    @Autowired
    private ScheduleUserService scheduleUserService;

    @GetMapping(path = "/scheduleUsers/{id}")
    public ResponseEntity<ScheduleUser> getScheduleUser(@PathVariable("id") String scheduleUserId) throws UserException {
        return ResponseEntity.ok().body(scheduleUserService.getScheduleUser(scheduleUserId));
    }



}
