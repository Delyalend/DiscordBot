package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import lombok.var;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:discordServer.properties")
public class ServiceRankAllocatorImpl implements ServiceRankAllocator {

    private DaoUser daoUser;
    private DaoTitle daoTitle;
    private JDA jda;
    private DiscordRankService discordRankService;

    @Value("${discordServerId}")
    private Long discordServerId;

    @Autowired
    public ServiceRankAllocatorImpl(DaoUser daoUser, DaoTitle daoTitle, JDA jda, DiscordRankService discordRankService) {
        this.daoTitle = daoTitle;
        this.daoUser = daoUser;
        this.jda = jda;

    }

    @Override
    public void assignTitle(Long userId) {

        User user = daoUser.getUserById(userId);
        List<Title> titles = daoTitle.getTitles();

        Title availableTitle = null;

        //Если пользователь не достиг последнего звания
        if (user.getPoints() < daoTitle.getLastTitle().getPoints()) {

            for (int i = 0; i < titles.size(); i++) {
                if (user.getPoints() >= titles.get(i).getPoints()) {
                    //получаем максимально возможное звание
                    availableTitle = titles.get(i);
                }
            }

            //Если Title у user нет, значит, это первое звание
            if (user.getTitle() == null) {
                daoTitle.assignTitleToUser(userId, availableTitle);
                discordRankService.addRoleToUser(userId, availableTitle);
            } else {
                System.out.println("Если доступное звание не равно текущему, то назначим новое звание");
                //Если доступное звание не равно текущему, то назначим новое звание
                assert availableTitle != null;

                if (!availableTitle.getName().equals(user.getTitle().getName())) {
                    daoTitle.assignTitleToUser(userId, availableTitle);
                    addCustomRoleWithPreDeleteRoles(userId, titles, availableTitle);
                } else {
                    System.out.println("если равно - то назначим то же самое звание - так как в дискорде звание может сбиться");
                    //если равно - то назначим то же самое звание - так как в дискорде звание может сбиться
                    addCustomRoleWithPreDeleteRoles(userId, titles, user.getTitle());
                }
            }


        }
    }

    private void addCustomRoleWithPreDeleteRoles(Long userId, List<Title> titleFromDb, Title title) {
        List<Role> titlesFromDiscord = jda.getRoles();
        titlesFromDiscord.forEach(titleDiscord -> {
            titleFromDb.forEach(titleDb -> {
                if (titleDb.getName().equals(titleDiscord.getName())) {
                    Objects.requireNonNull(jda.getGuildById(discordServerId)).removeRoleFromMember(userId, titleDiscord).queue(success -> {
                        discordRankService.addRoleToUser(userId, title);
                    });
                }
            });
        });
    }

    //Удалим все кастомные роли у юзера
    private void deleteCustomRolesFromUser(Long userId, List<Title> titleFromDb) {
        List<Role> titlesFromDiscord = jda.getRoles();
        titlesFromDiscord.forEach(titleDiscord -> {
            titleFromDb.forEach(titleDb -> {
                if (titleDb.getName().equals(titleDiscord.getName())) {
                    Objects.requireNonNull(jda.getGuildById(discordServerId)).removeRoleFromMember(userId, titleDiscord).queue();
                }
            });
        });
    }


}
