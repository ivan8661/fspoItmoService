package com.fspoitmo.fspoitmo.Controllers;


import com.fspoitmo.fspoitmo.Constants;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.ScheduleUpdate;
import com.fspoitmo.fspoitmo.Entities.News;
import com.fspoitmo.fspoitmo.Entities.Repositories.ScheduleUpdateRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
import com.fspoitmo.fspoitmo.FspoItmoApplication;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@ControllerAdvice
public class UniversityInfoController {

    private final ScheduleUpdateRepository scheduleUpdateRepository;

    @Autowired
    public UniversityInfoController(ScheduleUpdateRepository scheduleUpdateRepository) {
        this.scheduleUpdateRepository = scheduleUpdateRepository;
    }
    
    @GetMapping("/universityInfo")
    public ResponseEntity<String> universityInfo() throws JSONException, UserException {

        ScheduleUpdate scheduleUpdate = scheduleUpdateRepository.findByName(Constants.schedSyncDateConst);
        if(scheduleUpdate == null)
            throw new UserException(UserExceptionType.OBJECT_NOT_FOUND);

        JSONObject universityInfo = new JSONObject();

        universityInfo.put("_id", "FSPOITMO");
        universityInfo.put("name", "ФСПО ИТМО");
        universityInfo.put("serviceName", Constants.serviceName);
        universityInfo.put("referenceDate", scheduleUpdate.getSyncTime());
        universityInfo.put("referenceWeek", scheduleUpdate.getWeek());

        return ResponseEntity.ok().body(universityInfo.toString());
    }

    @GetMapping("/newsSources")
    public ResponseEntity<LinkedList<News>> sourceInfo() {

        LinkedList<News> news = new LinkedList<>();
        news.add(new News("-62056291", "Подслушано в ФСПО ИТМО", true, false));
        news.add(new News("-42119444", "Расписание ФСПО университета ИТМО", true, true));
        news.add(new News("-58939492", "Подслушано ИТМО", true, false));
        news.add(new News("-127149194", "ИТМЕМ", true, true));

        return ResponseEntity.ok().body(news);
    }


}
