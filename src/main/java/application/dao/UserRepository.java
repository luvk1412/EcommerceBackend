package application.dao;

import application.exception.AppException;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static application.dao.Mappers.getUserFromResultSet;
import static application.dao.Mappers.getUserMap;
import static application.dao.SqlConstants.*;
import static application.model.Constants.*;

@Repository
public class UserRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers(){
        return namedParameterJdbcTemplate.query(QUERY_USER_GET_ALL, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return getUserFromResultSet(resultSet);
            }
        });
    }

    public String getPasswordForId(int id){
        try {
            return namedParameterJdbcTemplate.queryForObject(QUERY_USER_GET_PASSWORD_BY_ID, getUserMap(id), String.class);
        }catch (EmptyResultDataAccessException ex){
            throw new AppException(HttpStatus.NOT_FOUND, MESSAGE_INVALID_USER_ID);
        }
    }

    public User getUserForEmail(String email){
        try{
            return namedParameterJdbcTemplate.queryForObject(QUERY_USER_GET_BY_EMAIL, getUserMap(email), new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return getUserFromResultSet(resultSet);
                }
            });
        }
        catch (EmptyResultDataAccessException ex){
            throw new AppException(HttpStatus.NOT_FOUND, MESSAGE_INVALID_EMAIL);
        }
    }


    public User addUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(QUERY_USER_ADD, getUserMap(user), keyHolder);
        } catch (DuplicateKeyException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_DUPLICATE_EMAIL);
        }
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    public void updateUser(User user) {
        try {
            namedParameterJdbcTemplate.update(QUERY_USER_UPDATE, getUserMap(user));
        } catch (DuplicateKeyException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_DUPLICATE_EMAIL);
        }
    }

    public void deleteUser(int id) {
        namedParameterJdbcTemplate.update(QUERY_USER_DELETE, getUserMap(id));
    }
}
