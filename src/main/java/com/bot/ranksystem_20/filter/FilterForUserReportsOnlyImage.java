package com.bot.ranksystem_20.filter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class FilterForUserReportsOnlyImage extends ListenerAdapter {

    Properties properties = new Properties();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        try {
            properties.load(new FileInputStream(new File("src\\main\\resources\\titleChannels.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (event.getTextChannel().getName().equals(properties.getProperty("userReports"))) {

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
