package com.abrahama.qttestapi.resources;

import com.abrahama.qttestapi.domain.Task;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;
import com.abrahama.qttestapi.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

    @Autowired
    TaskService taskService;

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest request) {
    //public String getAllTasks(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        //return "Authenticated! UserId: " + userId;

        List<Task> tasks = taskService.fetchAllTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);

//        Integer userIdAttribute = (Integer) request.getAttribute("userId");
//        if (userIdAttribute != null) {
//            int userId = userIdAttribute.intValue();
//            return "Authenticated! UserId: " + userId;
//        } else {
//            return "UserId attribute is null.";
//        }
    }

    @CrossOrigin
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(HttpServletRequest request,
                                            @PathVariable("taskId") Integer taskId) {
        int userId = (Integer) request.getAttribute("userId");
        Task task = taskService.fetchTaskById(userId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<Task> addTask(HttpServletRequest request,
                                        @RequestBody Map<String, Object> taskMap) {
        int userId = (Integer) request.getAttribute("userId");
        String title = (String) taskMap.get("title");
//        Long startDate = (Long) taskMap.get("startDate");
//        Long endDate = (Long) taskMap.get("endDate");
        Long startDate = Long.parseLong((String) taskMap.get("startDate"));
        Long endDate = Long.parseLong((String) taskMap.get("endDate"));
        String project = (String) taskMap.get("project");
        String description = (String) taskMap.get("description");
        String priority = (String) taskMap.get("priority");
        String file = (String) taskMap.get("file");
        try {
            Task task = taskService.addTask(userId, title, startDate, endDate, project, description, priority, file);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new QtResourceNotFoundException("Task not found");
        }
    }

    @CrossOrigin
    @PutMapping("/{taskId}")
    public ResponseEntity<Map<String, Boolean>> updateTask(HttpServletRequest request,
                                                           @PathVariable("taskId") Integer taskId,
                                                           @RequestBody Task task) {
        int userId = (Integer) request.getAttribute("userId");
        taskService.updateTask(userId, taskId, task);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
