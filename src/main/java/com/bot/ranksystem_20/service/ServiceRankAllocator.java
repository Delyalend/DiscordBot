package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;

import java.util.List;

public interface ServiceRankAllocator {

    void assignTitle(Long userId);
//    void deleteCustomRolesFromUser(User user, List<Title> titleFromDb);
//    void addCustomRoleToUserInDiscord(Long userId, Title title);
}
