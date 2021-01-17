package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoUser;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {

    private DaoUser daoUser;

    public PointServiceImpl(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    @Override
    public void addPointsToUser(Long userId, int amountPoints) {
        daoUser.addPointsToUser(userId, 1);
    }
}
