package com.bot.ranksystem_20.handlers;


import com.bot.ranksystem_20.service.RankImproverService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

//Цель класса: отловить событие лайка в определенном чате и оповестить
//определенной класс об этом с передачей информации о том, кто
//получил лайк
@Component
@PropertySource({"classpath:titleChannels.properties", "classpath:titleRoles.properties"})
public class HandlerReactionOnVictory extends ListenerAdapter {

    private RankImproverService rankImproverService;

    @Value("${userReports}")
    private String userReportsChannel;

    @Value("${admin}")
    private String titleRoleAdmin;


    public HandlerReactionOnVictory(RankImproverService rankImproverService,
                                    JDA jda) {
        this.rankImproverService = rankImproverService;
        jda.addEventListener(this);
    }


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        if (event.getTextChannel().getName().equals(userReportsChannel)) {


            if (event.getMember() != null) {

                List<Role> roles = event.getMember().getRoles();

                event.getMember().getRoles().forEach(role -> {
                    if (role.getName().equals(titleRoleAdmin)) {

                        User user = event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).complete().getAuthor();
                        try {
                            rankImproverService.improveRank(user.getIdLong());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        }
    }
}
