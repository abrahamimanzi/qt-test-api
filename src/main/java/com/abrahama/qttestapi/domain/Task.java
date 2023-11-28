package com.abrahama.qttestapi.domain;

public class Task {

    private Integer taskId;
    private Integer userId;
    private String title;
    private Long startDate;
    private Long endDate;
    private String project;
    private String description;
    private String priority;
    private String file;

    public Task(Integer taskId, Integer userId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
        this.description = description;
        this.priority = priority;
        this.file = file;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
