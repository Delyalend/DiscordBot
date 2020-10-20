package com.bot.ranksystem_20.handlers;

import com.bot.ranksystem_20.core.RankSystem;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

//Цель класса: отловить событие лайка в определенном чате и оповестить
//определенной класс об этом с передачей информации о том, кто
//получил лайк
@Component
@PropertySource({"classpath:titleChannels.properties","classpath:titleRoles.properties"})
public class HandlerReactionOnVictory extends ListenerAdapter {

    private RankSystem rankSystem;


    @Value("${userReports}")
    private String userReportsChannel;

    @Value("${admin}")
    private String titleRoleAdmin;


    @Autowired
    @SneakyThrows
    public HandlerReactionOnVictory(RankSystem rankSystem, JDA jda) {
        this.rankSystem = rankSystem;
        jda.addEventListener(this);
    }


    @Override
    @SneakyThrows
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

        if (event.getTextChannel().getName().equals(userReportsChannel)) {


            if (event.getMember() != null) {

                List<Role> roles = event.getMember().getRoles();

                event.getMember().getRoles().forEach(role -> {
                    if (role.getName().equals(titleRoleAdmin)) {

                        User user = event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).complete().getAuthor();
                        rankSystem.processRequestForUpgradeTitle(user.getIdLong());
                    }
                });

            }

        }
    }
}
