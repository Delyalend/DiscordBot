package com.bot.ranksystem_20.dao;

import com.bot.ranksystem_20.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoCardImpl implements DaoCard {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SELECT_CARD_BY_POINTS = "select * from card_db where points = ?";

    private RowMapper<Card> ROW_MAPPER_CARD = (ResultSet resultSet, int rowNum) -> {
        return Card.builder()
                .id(resultSet.getLong("id"))
                .points(resultSet.getInt("points"))
                .url(resultSet.getString("url"))
                .build();
    };

    @Override
    public Card getCardByPoints(int points) {
        Card lastCard = getLastCard();
        assert lastCard != null;
        if(points >= lastCard.getPoints()) {
            return lastCard;
        }

        List<Card> cards = jdbcTemplate.query(SELECT_CARD_BY_POINTS, ROW_MAPPER_CARD, points);
        if(!cards.isEmpty()) {
            return cards.get(0);
        }
        return null;
    }

    private Card getLastCard() {
        String SELECT_LAST_CARD = "select * from card_db order by points desc limit 1";
        List<Card> cards = jdbcTemplate.query(SELECT_LAST_CARD, ROW_MAPPER_CARD);
        if(!cards.isEmpty()){
            return cards.get(0);
        }
        return null;

    }
}
