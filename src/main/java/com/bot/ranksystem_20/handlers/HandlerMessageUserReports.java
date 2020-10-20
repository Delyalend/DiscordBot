package com.bot.ranksystem_20.handlers;

import lombok.SneakyThrows;
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
public class HandlerMessageUserReports extends ListenerAdapter {


    @Value("${userReports}")
    private String userReportsChannel;

    @Autowired
    @SneakyThrows
    public HandlerMessageUserReports(JDA jda) {
        jda.addEventListener(this);
    }

    @Override
    @SneakyThrows
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (event.getTextChannel().getName().equals(userReportsChannel)) {

            try {
                if (!event.getMessage().getAttachments().get(0).isImage()) {
                    event.getMessage().delete().submit();
                    event.getMessage().delete().submit();
                }
            } catch (Exception ex) {
                event.getMessage().delete().submit();
                event.getMessage().delete().submit();
            }

        }

    }
}
