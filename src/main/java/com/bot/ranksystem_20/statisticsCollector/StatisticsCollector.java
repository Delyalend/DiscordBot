package com.bot.ranksystem_20.statisticsCollector;

import com.bot.ranksystem_20.model.UserData;

public interface StatisticsCollector {

    UserData collectStatistics(Long userId);

}
