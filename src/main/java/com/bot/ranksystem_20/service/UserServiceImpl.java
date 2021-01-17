package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private DaoUser daoUser;

    public UserServiceImpl(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    @Override
    public boolean exists(Long userId) {
        return daoUser.getUserById(userId) != null;
    }

    @Override
    public User create(Long userId) {
        daoUser.createUser(userId);
        return daoUser.getUserById(userId);
    }

    @Override
    public User getById(Long userId) {
        return daoUser.getUserById(userId);
    }
}
