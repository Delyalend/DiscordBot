package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUserImpl implements ServiceUser {

    @Autowired
    private DaoUser daoUser;

    @Override
    public boolean userExists(Long userId) {
        return daoUser.getUserById(userId) == null;

    }

    @Override
    public void createUser(Long userId) {
        daoUser.createUser(userId);
    }
}
