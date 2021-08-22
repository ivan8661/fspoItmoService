package com.fspoitmo.fspoitmo.Controllers;


import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Services.AuthorizationService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class UserController {


    private AuthorizationService authorizationService;

    @Autowired
    public UserController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> regUser(@RequestBody String regData) throws JSONException, UserException {
        return ResponseEntity.ok().body(authorizationService.regFSPOITMOUser(regData));
    }
}
