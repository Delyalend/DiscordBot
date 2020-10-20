package com.bot.ranksystem_20.dao;

import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoUserImpl implements DaoUser {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DaoTitle daoTitle;


    //language=SQL
    private final String INSERT_ID_INTO_USER_DB = "insert into user_db (id, is_new_title,points,last_points_received) values (?,?,?,?)";


    //language=SQL
    private final String SELECT_USER_BY_ID = "select * from user_db where id = ?";

    private RowMapper<User> ROW_MAPPER_USER = (ResultSet resultSet, int rowNum) -> {

        User user = User.builder()
                .id(resultSet.getLong("id"))
                .lastPointsReceived(resultSet.getInt("last_points_received"))
                .points(resultSet.getInt("points"))
                .isNewTitle(resultSet.getBoolean("is_new_title"))
                .build();

        Title title = daoTitle.getCurrentTitleByUserId(user.getId());

        user.setTitle(title);

        return user;


    };

    //language=SQL
    private final String SELECT_ALL_FROM_USER_DB = "select * from user_db";

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(SELECT_ALL_FROM_USER_DB, ROW_MAPPER_USER);
    }

    @Override
    public User getUserById(long userId) {

        List<User> users = jdbcTemplate.query(SELECT_USER_BY_ID, ROW_MAPPER_USER, userId);

        if(!users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }



    @Override
    public void createUser(Long userId) {
        jdbcTemplate.update(INSERT_ID_INTO_USER_DB, userId, true,0,0);
    }

    @Override
    public void addPointsToUser(Long userId, int points) {

        System.out.println("нужно добавить " + points);

        //language=SQL
        String UPDATE_USER_DB_INSERT_POINTS_TO_USER_DB = "update user_db set points = points + " + points + ", last_points_received="+points+" where id = " + userId;
        jdbcTemplate.update(UPDATE_USER_DB_INSERT_POINTS_TO_USER_DB);
    }

    //language=SQL
    private final String UPDATE_IS_NEW_TITLE= "update user_db set is_new_title = ? where id = ?";

    @Override
    public void setValueIsNewTitle(Long userId, boolean value) {
        jdbcTemplate.update(UPDATE_IS_NEW_TITLE, value, userId);
    }



//    @Override
//    public void minusPointsAll(int points) {
//        //language=SQL
//        String UPDATE_POINTS_TO_ALL = "update user_db set points = points - " + points;
//        jdbcTemplate.update(UPDATE_POINTS_TO_ALL);
//    }

    @Override
    public void minusPointsOne(Long userId, int points) {

        User user= getUserById(userId);

        if (user.getPoints() < points) {
            String UPDATE_POINTS_TO_ONE = "update user_db set points = 0 where id = " + userId;
            jdbcTemplate.update(UPDATE_POINTS_TO_ONE);
        }
        else {
            //language=SQL
            String UPDATE_POINTS_TO_ONE = "update user_db set points = points - " + points + " where id = " + userId;
            jdbcTemplate.update(UPDATE_POINTS_TO_ONE);
        }
    }


}
