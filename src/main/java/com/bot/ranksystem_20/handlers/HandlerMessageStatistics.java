package com.bot.ranksystem_20.handlers;

import com.bot.ranksystem_20.displayReports.DisplayReport;
import com.bot.ranksystem_20.model.UserData;
import com.bot.ranksystem_20.statisticsCollector.StatisticsCollector;
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
public class HandlerMessageStatistics extends ListenerAdapter {

    private StatisticsCollector statisticsCollector;
    private DisplayReport displayReport;

    @Value("${statistics}")
    private String statisticsChannel;

    @Autowired
    public HandlerMessageStatistics(JDA jda, DisplayReport displayReport, StatisticsCollector statisticsCollector) {
        this.displayReport = displayReport;
        this.statisticsCollector = statisticsCollector;
        jda.addEventListener(this);
        System.out.println(jda.getRegisteredListeners());
    }


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getTextChannel().getName().equals(statisticsChannel)) {
            if (event.getMessage().getContentDisplay().equals("/rating")) {
                try {
                    UserData userData = statisticsCollector.collectStatistics(event.getAuthor().getIdLong());
                    displayReport.sendReportToDiscord(userData, "statistics");
                } catch (Exception ex) {
                    event.getTextChannel().sendMessage(event.getMessage().getAuthor().getName() + ", у вас нет звания! [если это не так, обратитесь в администрацию]").submit();
                }
            } else {
                if (!event.getMessage().getAuthor().isBot()) {
                    event.getMessage().delete().submit();
                }
            }
        }

    }
}
