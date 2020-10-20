package com.bot.ranksystem_20.statisticsCollector;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.UserData;
import com.bot.ranksystem_20.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsCollectorImpl implements StatisticsCollector {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoTitle daoTitle;

    @Override
    public UserData collectStatistics(Long userId) {
        User user = daoUser.getUserById(userId);

        return UserData.builder()
                .userId(userId)
                .currentPoints(user.getPoints())
                .currentTitle(user.getTitle())
                .lastPointsReceived(user.getLastPointsReceived())
                .nextTitle(daoTitle.getNextTitleForUser(userId))
                .isNewTitle(user.isNewTitle())
                .build();
    }
}
