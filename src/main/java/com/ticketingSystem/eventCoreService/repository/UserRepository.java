package com.ticketingSystem.eventCoreService.repository;

import com.ticketingSystem.eventCoreService.model.UserData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository implements UserRepositoryI {
    String USER_CREATION_SQL = "INSERT INTO tbl_users(user_id, email, password, role, banned)" +
            "VALUES (?,?, ?,?,?);";
    String GET_USER_BY_ID_SQL = "select * from tbl_user where id = ?";
    String GET_USER_BY_USERNAME = "select * from tbl_users where email = ?";
    private final JdbcTemplate jdbcTemplate;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserData createUser(UserData userData) {
        Object[] arguments = {
                userData.getId(),
                userData.getEmail(),
                userData.getPassword(),
                userData.getRole(),
                userData.isBanned()
        };
        jdbcTemplate.update(USER_CREATION_SQL,arguments);
        return userData;
    };

    @Override
    public UserData getUserById(UUID id) {
        return jdbcTemplate.queryForObject(
                GET_USER_BY_ID_SQL,
                new BeanPropertyRowMapper<>(UserData.class),
                new Object[]{id}
        );
    }

    @Override
    public UserData getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(
                GET_USER_BY_USERNAME,
                new BeanPropertyRowMapper<>(UserData.class),
                new Object[]{username}
        );
    }

    @Override
    public UserData searchUser(String user) {
        return null;
    }

    @Override
    public boolean banUserById(UUID id) {
        return false;
    }
}
