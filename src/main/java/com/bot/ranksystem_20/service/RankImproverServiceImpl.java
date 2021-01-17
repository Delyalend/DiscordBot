package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.displayReports.ReportDisplayer;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.model.UserData;
import com.bot.ranksystem_20.statisticsCollector.StatisticsCollector;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RankImproverServiceImpl implements RankImproverService {

    private PointService pointService;
    private RankAllocatorService rankAllocatorService;
    private StatisticsCollector statisticsCollector;
    private ReportDisplayer reportDisplayer;
    private UserService userService;


    public RankImproverServiceImpl(PointService pointService,
                                   RankAllocatorService rankAllocatorService,
                                   StatisticsCollector statisticsCollector,
                                   ReportDisplayer reportDisplayer,
                                   UserService userService) {
        this.pointService = pointService;
        this.rankAllocatorService = rankAllocatorService;
        this.statisticsCollector = statisticsCollector;
        this.reportDisplayer = reportDisplayer;
        this.userService = userService;
    }

    @Override
    @SneakyThrows
    public void improveRank(Long userId) throws IOException {

        User user;
        if(!userService.exists(userId)) {
            user = userService.create(userId);
        }
        else {
            user = userService.getById(userId);
        }

        pointService.addPointsToUser(userId, 1);

        //Назначить ему доступный ранг
        rankAllocatorService.assignRank(user);

        //Создать статистику
        UserData userData = statisticsCollector.collectStatistics(userId);

        //Передать статистику в дискорд
        reportDisplayer.sendReportToDiscord(userData, "serverReports");
    }
}
