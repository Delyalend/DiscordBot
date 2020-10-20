package com.bot.ranksystem_20.pointsCalculator;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointsCalculatorSimplePoint implements PointsCalculator {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private ServiceUser serviceUser;

    @Override
    public void calculatePoints(Long userId) {

        //Если юзера нет - то создать его
        if (serviceUser.userExists(userId)) {
            System.out.println("ЮЗЕРА НЕТ БЕГУ СОЗДАВАТЬ!");
            serviceUser.createUser(userId);
        }

        daoUser.addPointsToUser(userId, 1);
    }
}
