package com.bot.ranksystem_20.dao;

import com.bot.ranksystem_20.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoTitleImpl implements DaoTitle {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String UPDATE_USER_DB = "update user_db set title_id = ?, is_new_title = ? where id = ?";

    //language=SQL
    private final String SELECT_LAST_TITLE_FROM_TITLE_DB = "select * from title_db order by points desc limit 1";

    //language=SQL
    private final String SELECT_CURRENT_TITLE_FROM_TITLE_DB = "select title_db.id, title_db.name, title_db.points from title_db " +
            "left join user_db on user_db.title_id = title_db.id " +
            "where user_db.id = ?";

    //language=SQL
    private final String SELECT_TITLE_BY_TITLE_ID_FROM_TITLE_DB = "select * from title_db where id = ?";

    //language=SQL
    private final String SELECT_NEXT_TITLE = "select * from title_db where points > ?";

    //language=SQL
    private final String SELECT_TITLES_FROM_TITLE_DB = "select * from title_db order by points";

    private RowMapper<Title> ROW_MAPPER_TITLE = (ResultSet resultSet, int rowNum) -> {
        return Title.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .points(resultSet.getInt("points"))
                .build();
    };

    @Override
    public List<Title> getTitles() {
        return jdbcTemplate.query(SELECT_TITLES_FROM_TITLE_DB, ROW_MAPPER_TITLE);
    }

    @Override
    public Title getTitleByTitleId(Long titleId) {
        List<Title> titles = jdbcTemplate.query(SELECT_TITLE_BY_TITLE_ID_FROM_TITLE_DB, ROW_MAPPER_TITLE, titleId);
        if(!titles.isEmpty()) {
            return titles.get(0);
        }
        return null;
    }

    @Override
    public Title getLastTitle() {
        List<Title> titles = jdbcTemplate.query(SELECT_LAST_TITLE_FROM_TITLE_DB, ROW_MAPPER_TITLE);
        if(!titles.isEmpty()) {
            return titles.get(0);
        }
        return null;
    }


    @Override
    public void assignTitleToUser(Long userId, Title title) {
        jdbcTemplate.update(UPDATE_USER_DB,title.getId(), true, userId);
    }



    @Override
    public Title getNextTitleForUser(Long userId) {

        Title lastTitle = getLastTitle();
        Title currentTitle = getCurrentTitleByUserId(userId);
        if(currentTitle.getPoints() >= lastTitle.getPoints()) {
            return null;
        }

        List<Title> titles = jdbcTemplate.query(SELECT_NEXT_TITLE, ROW_MAPPER_TITLE, currentTitle.getPoints());

        if(!titles.isEmpty()) {
            return titles.get(0);
        }

        return null;

    }

    @Override
    public Title getCurrentTitleByUserId(Long userId) {
        List<Title> titles = jdbcTemplate.query(SELECT_CURRENT_TITLE_FROM_TITLE_DB, ROW_MAPPER_TITLE, userId);
        if(!titles.isEmpty()) {
            return titles.get(0);
        }
        return null;
    }
}
