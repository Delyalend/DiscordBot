package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import org.openjdk.jmh.annotations.Benchmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:discordServer.properties")
public class RankAllocatorServiceImpl implements RankAllocatorService {

    private DaoTitle daoTitle;
    private DiscordRankService discordRankService;
    private RankService rankService;

    @Value("${discordServerId}")
    private Long discordServerId;

    @Autowired
    public RankAllocatorServiceImpl(DaoTitle daoTitle,
                                    DiscordRankService discordRankService,
                                    RankService rankService) {
        this.daoTitle = daoTitle;
        this.rankService = rankService;
        this.discordRankService = discordRankService;
    }

    @Override
    public void assignRank(User user) throws Exception {

        if (userHasLastRank(user)) {

            Title availableTitle = rankService.getMaxAvailableRank(user);


            if (!user.hasRank()) {
                rankService.assignTitleToUser(user, availableTitle);
                discordRankService.addRoleToUser(user.getId(), availableTitle);
            } else {

                //Если доступное звание не равно текущему, то назначим новое звание
                if (!availableTitle.getName().equals(user.getTitle().getName())) {
                    daoTitle.assignTitleToUser(user.getId(), availableTitle);
                    discordRankService.clearRolesToUser(user.getId());
                    discordRankService.addRoleToUser(user.getId(), availableTitle);
                } else {
                    //если равно - то назначим то же самое звание - так как в дискорде звание может сбиться
                    discordRankService.clearRolesToUser(user.getId());
                    discordRankService.addRoleToUser(user.getId(), user.getTitle());

                }
            }


        }
    }

    private boolean userHasLastRank(User user) {
        return user.getPoints() < daoTitle.getLastTitle().getPoints();
    }
}