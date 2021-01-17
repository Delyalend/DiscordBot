package com.bot.ranksystem_20.service;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.model.Title;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:discordServer.properties")
public class DiscordRankServiceImpl implements DiscordRankService {

    private JDA jda;
    private DaoTitle daoTitle;

    @Value("${discordServerId}")
    private Long discordServerId;


    public DiscordRankServiceImpl(JDA jda, DaoTitle daoTitle) {
        this.jda = jda;
        this.daoTitle = daoTitle;
    }

    @Override
    public void addRoleToUser(Long userId, Title title) {
        final Guild guild = jda.getGuildById(discordServerId);
        final Role role = guild.getRolesByName(title.getName(), false).get(0);
        guild.addRoleToMember(userId, role).queue();
    }

    @Override
    public void clearRolesToUser(Long userId) throws Exception {
        Guild guild = jda.getGuildById(discordServerId);

        if (guild == null) {
            throw new Exception("Guild with id = " + discordServerId + " not found");
        }

        List<Title> ranks = daoTitle.getTitles();
        List<Role> roles = jda.getRoles();

        roles.forEach(role ->
                ranks.forEach(rank -> {
                    if (rank.getName().equals(role.getName())) {
                        guild.removeRoleFromMember(userId, role).complete();
                    }
                })
        );

    }
}
