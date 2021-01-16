package com.bot.ranksystem_20.handlers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@PropertySource("classpath:titleChannels.properties")
public class HandlerMessageServerReports extends ListenerAdapter {


    @Value("${serverReports}")
    private String serverReportsChannel;

    @Autowired
    public HandlerMessageServerReports(JDA jda) {
        jda.addEventListener(this);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (event.getTextChannel().getName().equals(serverReportsChannel)) {
            if (!event.getMessage().getAuthor().isBot()) {
                event.getMessage().delete().submit();
            }
        }

    }
}
