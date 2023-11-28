package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.Assignee;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;

import java.util.List;

public interface AssigneeService {

    List<Assignee> fetchAllAssignees(Integer userId, Integer taskId);

    Assignee fetchAssigneeById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException;

    Assignee addAssignee(Integer taskId, Integer userId, Long taskDate) throws QtBadRequestException;

    void updateAssignee(Integer userId, Integer taskId, Integer assigneeId, Assignee assignee) throws QtBadRequestException;

    void removeAssignee(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException;
}
