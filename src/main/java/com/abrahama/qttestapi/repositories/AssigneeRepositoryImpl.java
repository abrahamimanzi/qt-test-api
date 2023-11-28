package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.Assignee;
import com.abrahama.qttestapi.exceptions.QtBadRequestException;
import com.abrahama.qttestapi.exceptions.QtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AssigneeRepositoryImpl implements AssigneeRepository{

    private static final String SQL_FIND_BY_ID = "SELECT ASSIGNEE_ID, TASK_ID, USER_ID, TASK_DATE " +
            "FROM QT_ASSIGNEES WHERE USER_ID = ? AND TASK_ID = ? AND ASSIGNEE_ID = ? ";

//    private static final String SQL_FIND_BY_ID = "SELECT ASSIGNEE_ID, TASK_ID, USER_ID, TASK_DATE " +
//            "FROM QT_ASSIGNEES WHERE TASK_ID = ? AND ASSIGNEE_ID = ? ";

    private static final String SQL_CREATE = "INSERT INTO QT_ASSIGNEES (ASSIGNEE_ID, TASK_ID, USER_ID, TASK_DATE) VALUES (NEXTVAL('QT_ASSIGNEES_SEQ'), ?,?,? )";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Assignee> findAll(Integer userId, Integer taskId) {
        return null;
    }

//    @Override
//    public Assignee findById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {
//        try {
//            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, taskId, assigneeId}, assigneeRowMapper);
//        }catch (Exception e){
//            throw new QtResourceNotFoundException("Assignees not found");
//        }
//    }

//    @Override
//    public Assignee findById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {
//        try {
//            Assignee assignee = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, taskId, assigneeId}, assigneeRowMapper);
//            if (assignee == null) {
//                throw new QtResourceNotFoundException("Assignee not found");
//            }
//            return assignee;
//        } catch (Exception e) {
//            throw new QtResourceNotFoundException("Assignee not found");
//        }
//    }

    @Override
    public Assignee findById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {
        try {
            String query = SQL_FIND_BY_ID;
            Object[] params = {userId, taskId, assigneeId};
            Assignee assignee = jdbcTemplate.queryForObject(query, params, assigneeRowMapper);
            if (assignee == null) {
                throw new QtResourceNotFoundException("Assignee not found");
            }
            return assignee;
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            throw new QtResourceNotFoundException("Assignee not found");
        }
    }



    @Override
    public Integer create(Integer taskId, Integer userId, Long taskDate) throws QtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, taskId);
                ps.setInt(2, userId);
                ps.setLong(3, taskDate);
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("ASSIGNEE_ID");
        }catch (Exception e){
            throw new QtBadRequestException("Invalid request");
        }
    }


    @Override
    public void update(Integer userId, Integer taskId, Integer assigneeId, Assignee assignee) throws QtBadRequestException {

    }

    @Override
    public void removeById(Integer userId, Integer taskId, Integer assigneeId) throws QtResourceNotFoundException {

    }


    private RowMapper<Assignee> assigneeRowMapper = ((rs, rowNum) -> {
        return new Assignee(rs.getInt("ASSIGNEE_ID"),
                rs.getInt("TASK_ID"),
                rs.getInt("USER_ID"),
                rs.getLong("TASK_DATE"));
    });
}
