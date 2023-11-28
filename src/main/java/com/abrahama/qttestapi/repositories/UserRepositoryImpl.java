package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.User;
import com.abrahama.qttestapi.exceptions.QtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO QT_USERS(USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) " +
            "VALUES (NEXTVAL('QT_USERS_SEQ'),?,?,?,?)";

    private static final String SQL_COUNT_BY_EMAIL ="SELECT COUNT(*) FROM QT_USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD FROM QT_USERS WHERE USER_ID = ?";

    private static final String SQL_FIND_ALL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM QT_USERS";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME,EMAIL,PASSWORD FROM QT_USERS WHERE EMAIL = ?";

    private static final String SQL_UPDATE = "UPDATE QT_USERS " +
            "SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ? " +
            "WHERE USER_ID = ?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM QT_USERS " +
            "WHERE USER_ID = ?";

    private static final String SQL_CHANGE_PASSWORD_BY_ID = "UPDATE QT_USERS " +
            "SET PASSWORD = ? " +
            "WHERE USER_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws QtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,email);
                //ps.setString(4,password);
                ps.setString(4,hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch (Exception e){
            throw new QtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws QtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            //if(!password.equals(user.getPassword()))
            if (!BCrypt.checkpw(password, user.getPassword()))
                throw new QtAuthException("Invalid email/password");
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new QtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) throws QtAuthException {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

//    @Override
//    public void updateUser(Integer userId, String firstName, String lastName, String email) throws QtAuthException {
//        try {
//            jdbcTemplate.update(SQL_UPDATE, userId, firstName, lastName, email);
//        } catch (Exception e) {
//            throw new QtAuthException("Failed to update user details");
//        }
//    }

    @Override
    public void updateUser(Integer userId, String firstName, String lastName, String email) throws QtAuthException {
        try {
            jdbcTemplate.update(SQL_UPDATE, firstName, lastName, email, userId);
        } catch (Exception e) {
            throw new QtAuthException("Failed to update user details");
        }
    }

    @Override
    public void deleteUser(Integer userId) throws QtAuthException {
        try {
            jdbcTemplate.update(SQL_DELETE_BY_ID, userId);
        } catch (Exception e) {
            throw new QtAuthException("Failed to delete user");
        }
    }

    @Override
    public void changeUserPassword(Integer userId, String newPassword) throws QtAuthException {
        try {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            jdbcTemplate.update(SQL_CHANGE_PASSWORD_BY_ID, hashedPassword, userId);
        } catch (Exception e) {
            throw new QtAuthException("Failed to change user password");
        }
    }

    private RowMapper<User> userRowMapper =((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });

}
