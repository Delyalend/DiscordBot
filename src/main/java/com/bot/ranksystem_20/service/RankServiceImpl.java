package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RankServiceImpl implements RankService {

    private DaoTitle daoTitle;

    public RankServiceImpl(DaoTitle daoTitle) {
        this.daoTitle = daoTitle;
    }

    @Override
    public Title getMaxAvailableRank(User user) throws Exception {

        List<Title> titles = daoTitle.getTitles();

        int points = user.getPoints();
        Optional<Title> titleOptional = titles.stream()
                .filter(title -> points >= title.getPoints())
                .findFirst();

        if(titleOptional.isEmpty()) {
            throw new Exception("Max available rank not found!");
        }

        return titleOptional.get();

    }

    @Override
    public void assignTitleToUser(User user, Title title) {
        daoTitle.assignTitleToUser(user.getId(), title);
    }
}
