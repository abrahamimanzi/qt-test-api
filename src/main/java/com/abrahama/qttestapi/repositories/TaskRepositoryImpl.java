package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.Task;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.abrahama.qttestapi.domain.Task;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private static final String SQL_FIND_ALL = "SELECT T.TASK_ID, T.USER_ID, T.TITLE, T.START_DATE, T.END_DATE, T.PROJECT, T.DESCRIPTION, T.PRIORITY, T.FILE " +
            "FROM QT_ASSIGNEES A RIGHT OUTER JOIN QT_TASKS T ON T.TASK_ID = A.TASK_ID " +
            "WHERE T.USER_ID = ? GROUP BY T.TASK_ID";
    private static final String SQL_FIND_BY_ID = "SELECT T.TASK_ID, T.USER_ID, T.TITLE, T.START_DATE, T.END_DATE, T.PROJECT, T.DESCRIPTION, T.PRIORITY, T.FILE " +
            "FROM QT_ASSIGNEES A RIGHT OUTER JOIN QT_TASKS T ON T.TASK_ID = A.TASK_ID " +
            "WHERE T.USER_ID = ? AND T.TASK_ID = ? GROUP BY T.TASK_ID";
    private static final String SQL_FIND_TASK_BY_ID = "SELECT T.TASK_ID, T.USER_ID, T.TITLE, T.START_DATE, T.END_DATE, T.PROJECT, T.DESCRIPTION, T.PRIORITY, T.FILE " +
            "FROM QT_ASSIGNEES A RIGHT OUTER JOIN QT_TASKS T ON T.TASK_ID = A.TASK_ID " +
            "WHERE T.TASK_ID = ? GROUP BY T.TASK_ID";
    private static final  String SQL_CREATE = "INSERT INTO QT_TASKS (TASK_ID, USER_ID, TITLE, START_DATE, END_DATE, PROJECT, DESCRIPTION, PRIORITY, FILE) VALUES(NEXTVAL('QT_TASKS_SEQ'), ?,?,?,?,?,?,?,? )";


    private static final String SQL_UPDATE = "UPDATE QT_TASKS SET TITLE = ?, START_DATE = ?, END_DATE = ?, PROJECT = ?, DESCRIPTION = ?, PRIORITY = ?, FILE = ? " +
            "WHERE USER_ID =? AND TASK_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Task> findAll(Integer userId) throws QtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, taskRowMapper);
    }

    @Override
    public Task findById(Integer userId, Integer taskId) throws QtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, taskId}, taskRowMapper);
        }catch (Exception e){
            throw new QtResourceNotFoundException("Task not found");
        }
    }

    @Override
    public Task findTaskById(Integer taskId) throws QtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_TASK_BY_ID, new Object[]{taskId}, taskRowMapper);
        }catch (Exception e){
            throw new QtResourceNotFoundException("Task not found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtResourceNotFoundException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setLong(3,startDate);
                ps.setLong(4, endDate);
                ps.setString(5, project);
                ps.setString(6,description);
                ps.setString(7, priority);
                ps.setString(8,file);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("TASK_ID");

        }catch (Exception e){
            throw new QtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer taskId, Task task) throws QtBadRequestException {
    //public void update(Integer userId, Integer taskId, String title, Long startDate, Long endDate, String project, String description, String priority, String file) throws QtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{task.getTitle(), task.getStartDate(), task.getEndDate(), task.getProject(), task.getDescription(), task.getPriority(), task.getFile(), userId, taskId});
        }catch (Exception e){
            throw new QtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer taskId) {

    }

    private RowMapper<Task> taskRowMapper = ((rs, rowNum) -> {
        return new Task(rs.getInt("TASK_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getLong("START_DATE"),
                rs.getLong("END_DATE"),
                rs.getString("PROJECT"),
                rs.getString("DESCRIPTION"),
                rs.getString("PRIORITY"),
                rs.getString("FILE")

        );
    });

}
