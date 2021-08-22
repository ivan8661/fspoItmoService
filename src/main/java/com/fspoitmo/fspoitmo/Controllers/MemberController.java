package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Services.GroupService;
import com.fspoitmo.fspoitmo.Services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class MemberController {

    private final ProfessorService professorService;

    private final GroupService groupService;

    @Autowired
    public MemberController(ProfessorService professorService, GroupService groupService) {
        this.professorService = professorService;
        this.groupService = groupService;
    }

    @GetMapping("/professors")
    public ResponseEntity<List<Professor>> professors(@RequestParam Map<String, String> params) throws NoSuchFieldException {
        return ResponseEntity.ok().body(professorService.getProfessors(params));
    }

    @GetMapping("/professors/{professorId}")
    public ResponseEntity<Professor> professors(@PathVariable("professorId") String professorId) throws UserException, UserException {
        return ResponseEntity.ok().body(professorService.getProfessor(professorId));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<PupilGroup>> groups(@RequestParam Map<String, String> params) throws NoSuchFieldException {
        return ResponseEntity.ok().body(groupService.getGroups(params));
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<PupilGroup> group(@PathVariable("groupId") String groupId) throws UserException {
        return ResponseEntity.ok().body(groupService.getGroup(groupId));
    }
}
