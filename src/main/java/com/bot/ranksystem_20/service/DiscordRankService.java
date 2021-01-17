package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.model.Title;

public interface DiscordRankService {
    void addRoleToUser(Long userId, Title title);
    void clearRolesToUser(Long userId) throws Exception;
}
