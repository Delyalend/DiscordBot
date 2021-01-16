package com.bot.ranksystem_20.developerConsole;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.ServiceRankAllocator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
@PropertySource("classpath:titleChannels.properties")
public class DevConsoleMinusPointsToAll extends ListenerAdapter {

    private ServiceRankAllocator serviceRankAllocator;
    private DaoUser daoUser;

    @Value("${console}")
    private String consoleChannel;

    @Autowired
    public DevConsoleMinusPointsToAll(ServiceRankAllocator serviceRankAllocator, JDA jda, DaoUser daoUser) {
        this.daoUser = daoUser;
        jda.addEventListener(this);
        this.serviceRankAllocator = serviceRankAllocator;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (event.getTextChannel().getName().equals(consoleChannel)) {

            if (!event.getMessage().getAuthor().isBot()) {
                try {
                    String[] commands = event.getMessage().getContentDisplay().split(" ");
                    int number = Integer.parseInt(commands[1]);
                    List<User> users = daoUser.getUsers();
                    users.forEach(user -> {
                        daoUser.minusPointsOne(user.getId(), number);
                        serviceRankAllocator.assignTitle(user.getId());
                    });
                    event.getTextChannel().sendMessage("Команда [minusToAll] обрабатывается...").submit();
                } catch (Exception ex) {
                    event.getMessage().delete().submit();
                }
            }
        }
    }
}
