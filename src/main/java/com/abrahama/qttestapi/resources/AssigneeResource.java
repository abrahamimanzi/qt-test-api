package com.abrahama.qttestapi.resources;

import com.abrahama.qttestapi.domain.Assignee;
import com.abrahama.qttestapi.services.AssigneeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks/{taskId}/assignees")
public class AssigneeResource {

    @Autowired
    AssigneeService assigneeService;

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<Assignee> addAssignee(HttpServletRequest request,
                                                @PathVariable("taskId") Integer taskId,
                                                @RequestBody Map<String, Object> assigneeMap) {
        int userId = (Integer) assigneeMap.get("userId");
        Long taskDate = Long.parseLong((String) assigneeMap.get("taskDate"));
        Assignee assignee = assigneeService.addAssignee(taskId, userId, taskDate);
        return new ResponseEntity<>(assignee, HttpStatus.CREATED);
    }





}
