package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.Task;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;

import java.util.List;

public interface TaskService {


    List<Task> fetchAllTasks(Integer userId);

    Task fetchTaskById(Integer taskId, Integer userId) throws QtResourceNotFoundException;

    Task addTask(Integer userId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException;

    //void updateTask(Integer userId, Integer taskId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException;
    void updateTask(Integer userId, Integer taskId, Task task) throws QtBadRequestException;

    void removeTaskWithAllAssignees(Integer userId, Integer taskId) throws QtResourceNotFoundException;

}
