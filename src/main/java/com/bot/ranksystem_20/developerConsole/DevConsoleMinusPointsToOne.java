package com.bot.ranksystem_20.developerConsole;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.service.ServiceRankAllocator;
import lombok.SneakyThrows;
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

    private ServiceRankAllocator serviceRankAllocator;

    @SneakyThrows
    @Autowired
    public DevConsoleMinusPointsToOne(DaoUser daoUser, JDA jda, ServiceRankAllocator serviceRankAllocator, DaoTitle daoTitle) {
        this.serviceRankAllocator = serviceRankAllocator;
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
                    serviceRankAllocator.assignTitle(member.getIdLong());
                    serviceRankAllocator.assignTitle(member.getIdLong());
                    event.getTextChannel().sendMessage("Команда [minusToOne] обрабатывается...").submit();
                } catch (Exception ex) {
                    event.getMessage().delete().submit();
                }
            }
        }
    }
}

