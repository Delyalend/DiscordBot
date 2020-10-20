package com.bot.ranksystem_20.dao;

import com.bot.ranksystem_20.model.Title;

import java.util.List;

public interface DaoTitle {

    List<Title> getTitles();
    Title getTitleByTitleId(Long titleId);

    Title getLastTitle();

    void assignTitleToUser(Long userId, Title title);

    Title getNextTitleForUser(Long userId);

    Title getCurrentTitleByUserId(Long userId);

}
