package com.bot.ranksystem_20.service;

public interface ServiceUser {

    boolean userExists(Long userId);

    void createUser(Long userId);

}
