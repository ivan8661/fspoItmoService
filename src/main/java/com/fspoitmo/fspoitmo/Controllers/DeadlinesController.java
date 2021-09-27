package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.ListAnswer;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class DeadlinesController {


    @PostMapping("/deadlineSources")
    public ResponseEntity<ListAnswer> deadlineSources(@RequestBody String body) {
        return ResponseEntity.ok().body(ListAnswer.EMPTY);
    }

    @PostMapping("/deadlines")
    public ResponseEntity<ListAnswer> deadlines(@RequestBody String body) {
        return ResponseEntity.ok().body(ListAnswer.EMPTY);
    }

    @PostMapping("/deadlines/{deadlineId}")
    public ResponseEntity<Object> deadlines(@RequestBody String body, @PathVariable ("deadlineId") String deadlineId) throws UserException {
        throw new UserException(UserExceptionType.OBJECT_NOT_FOUND);
    }

}
