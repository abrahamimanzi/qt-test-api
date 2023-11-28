package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.Task;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;
import com.abrahama.qttestapi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> fetchAllTasks(Integer userId) {
        return taskRepository.findAll(userId);
    }

    @Override
    public Task fetchTaskById(Integer taskId, Integer userId) throws QtResourceNotFoundException {
        return taskRepository.findById(userId, taskId);
    }

    @Override
    public Task addTask(Integer userId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException {
        int taskId = taskRepository.create(userId, title, startDate, endDate, project, description, priority, file);
        return taskRepository.findById(userId, taskId);
    }

    @Override
    public void updateTask(Integer userId, Integer taskId, Task task) throws QtBadRequestException {
    //public void updateTask(Integer userId, Integer taskId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException {
        taskRepository.update(userId, taskId, task);
    }

    @Override
    public void removeTaskWithAllAssignees(Integer userId, Integer taskId) throws QtResourceNotFoundException {

    }
}
