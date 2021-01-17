package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;

public interface RankService {
    Title getMaxAvailableRank(User user) throws Exception;
    void assignTitleToUser(User user, Title title);
}
