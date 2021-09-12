package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
import com.fspoitmo.fspoitmo.Services.ImportService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
@RequestMapping("fspoitmo")
public class UtilController {

    @Autowired
    private ImportService importService;


    @GetMapping(path="/import")
    public ResponseEntity<String> getImport(@RequestBody String password) throws JSONException, UserException {
        JSONObject passwordObject = new JSONObject(password);
        if(!passwordObject.has("password")) {
            throw new UserException(UserExceptionType.FORBIDDEN, null, null);
        } else {
            importService.updateFSPOTeachers();
            importService.updateFSPOGroups();
            importService.updateFSPOSubjects();
            importService.updateFSPOLessons();
        }
        return ResponseEntity.ok().body("successful");
    }

}
