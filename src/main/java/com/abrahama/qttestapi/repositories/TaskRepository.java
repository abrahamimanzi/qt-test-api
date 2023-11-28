package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.Task;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;

import java.util.List;

public interface TaskRepository {


    List<com.abrahama.qttestapi.domain.Task> findAll(Integer userId) throws QtResourceNotFoundException;

    Task findById(Integer userId, Integer taskId) throws QtResourceNotFoundException;

    Task findTaskById(Integer taskId) throws QtResourceNotFoundException;

    Integer create(Integer userId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtResourceNotFoundException;

    //void update(Integer userId, Integer taskId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException;
    void update(Integer userId, Integer taskId, Task task) throws QtBadRequestException;

    void removeById(Integer userId, Integer taskId);

}
