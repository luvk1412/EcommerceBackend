package application.dao;

import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static application.dao.Mappers.getUserMap;
import static application.dao.SqlConstants.*;

@Repository
public class UserRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<User> getAllUsers(){
        return namedParameterJdbcTemplate.query(QUERY_USER_GET_ALL, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return new User(resultSet);
            }
        });
    }

    public User addUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_USER_ADD, getUserMap(user), keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    public void updateUser(User user) {
        namedParameterJdbcTemplate.update(QUERY_USER_UPDATE, getUserMap(user));
    }

    public void deleteUser(int id) {
        namedParameterJdbcTemplate.update(QUERY_USER_DELETE, getUserMap(id));
    }
}
