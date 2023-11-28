package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.Assignee;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;
import com.abrahama.qttestapi.repositories.AssigneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssigneeServiceImpl implements AssigneeService{

    @Autowired
    AssigneeRepository assigneeRepository;

    @Override
    public List<Assignee> fetchAllAssignees(Integer userId, Integer taskId) {
        return null;
    }

    @Override
    public Assignee fetchAssigneeById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {
        return null;
    }

    @Override
    public Assignee addAssignee(Integer taskId, Integer userId, Long taskDate) throws QtBadRequestException {
        int assigneeId = assigneeRepository.create(taskId, userId, taskDate);
        return assigneeRepository.findById(taskId, userId, assigneeId);
    }

    @Override
    public void updateAssignee(Integer userId, Integer taskId, Integer assigneeId, Assignee assignee) throws QtBadRequestException {

    }

    @Override
    public void removeAssignee(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {

    }
}
