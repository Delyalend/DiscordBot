package com.bot.ranksystem_20.developerConsole;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.RankAllocatorService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@PropertySource("classpath:titleChannels.properties")
public class DevConsoleMinusPointsToOne extends ListenerAdapter {


    private DaoUser daoUser;

    @Value("${console}")
    private String consoleChannel;

    private RankAllocatorService rankAllocatorService;

    @Autowired
    public DevConsoleMinusPointsToOne(DaoUser daoUser, JDA jda, RankAllocatorService rankAllocatorService, DaoTitle daoTitle) {
        this.rankAllocatorService = rankAllocatorService;
        jda.addEventListener(this);
        this.daoUser = daoUser;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (event.getTextChannel().getName().equals(consoleChannel)) {

            if (!event.getMessage().getAuthor().isBot()) {
                try {
                    String[] commands = event.getMessage().getContentDisplay().split(" ");
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    int count = Integer.parseInt(commands[2]);

                    daoUser.minusPointsOne(member.getIdLong(), count);

                    User user = daoUser.getUserById(member.getIdLong());

                    rankAllocatorService.assignRank(user);
                    //serviceRankAllocator.assignRank(user);
                    event.getTextChannel().sendMessage("Команда [minusToOne] обрабатывается...").submit();
                } catch (Exception ex) {
                    event.getMessage().delete().submit();
                }
            }
        }
    }
}

