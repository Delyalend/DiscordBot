package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.model.User;

public interface UserService {

    boolean exists(Long userId);

    User create(Long userId);

    User getById(Long userId);

}
