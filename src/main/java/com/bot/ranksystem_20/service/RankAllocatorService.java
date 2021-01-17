package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.model.User;

public interface RankAllocatorService {

    void assignRank(User user) throws Exception;

}
