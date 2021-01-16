package com.bot.ranksystem_20.core;


import com.bot.ranksystem_20.displayReports.DisplayReport;
import com.bot.ranksystem_20.model.UserData;
import com.bot.ranksystem_20.pointsCalculator.PointsCalculator;
import com.bot.ranksystem_20.service.ServiceRankAllocator;
import com.bot.ranksystem_20.statisticsCollector.StatisticsCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RankSystemImpl implements RankSystem {

    @Autowired
    private ServiceRankAllocator serviceRankAllocator;

    @Autowired
    private PointsCalculator pointsCalculator;

    @Autowired
    private StatisticsCollector statisticsCollector;

    @Autowired
    private DisplayReport displayReport;

    @Override
    public void processRequestForUpgradeTitle(Long userId) throws IOException {

        //Прибавить некоторое количество очков юзеру
        pointsCalculator.calculatePoints(userId);

        //Назначить ему доступный ранг
        serviceRankAllocator.assignTitle(userId);

        //Создать статистику
        UserData userData = statisticsCollector.collectStatistics(userId);

        //Передать статистику в дискорд
        displayReport.sendReportToDiscord(userData, "serverReports");
    }
}
