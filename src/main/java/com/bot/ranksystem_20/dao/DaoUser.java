package com.bot.ranksystem_20.dao;

import com.bot.ranksystem_20.model.User;

import java.util.List;

public interface DaoUser {

    List<User> getUsers();

    User getUserById(long userId);

    void createUser(Long userId);

    void addPointsToUser(Long userId, int points);

    void setValueIsNewTitle(Long userId,boolean value);

//    void minusPointsAll(int points);

    void minusPointsOne(Long userId, int points);
}
