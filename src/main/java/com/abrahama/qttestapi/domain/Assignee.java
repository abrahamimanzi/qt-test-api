package com.abrahama.qttestapi.domain;

public class Assignee {

    private Integer assigneeId;
    private Integer task_id;
    private Integer user_id;
    private Long task_date;

    public Assignee(Integer assigneeId, Integer task_id, Integer user_id, Long task_date) {
        this.assigneeId = assigneeId;
        this.task_id = task_id;
        this.user_id = user_id;
        this.task_date = task_date;
    }

    public Assignee() {

    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Long getTask_date() {
        return task_date;
    }

    public void setTask_date(Long task_date) {
        this.task_date = task_date;
    }

    public void setUserId(Integer userId) {
    }

    public void setTaskDate(long taskDate) {
    }
}
