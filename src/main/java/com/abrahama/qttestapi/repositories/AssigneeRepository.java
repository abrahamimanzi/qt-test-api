package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.Assignee;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;

import java.util.List;

public interface AssigneeRepository {

    List<Assignee> findAll(Integer taskId, Integer userId);

    Assignee findById(Integer taskId, Integer userId, Integer assigneeId) throws QtResourceNotFoundException;

    Integer create(Integer taskId, Integer userId, Long taskDate) throws QtBadRequestException;

    void update(Integer userId, Integer taskId, Integer assigneeId, Assignee assignee) throws QtBadRequestException;

    void removeById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException;

}
