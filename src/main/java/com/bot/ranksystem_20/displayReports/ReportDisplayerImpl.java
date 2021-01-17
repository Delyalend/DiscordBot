package com.bot.ranksystem_20.displayReports;

import com.bot.ranksystem_20.dao.DaoCard;
import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.Card;
import com.bot.ranksystem_20.model.UserData;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class ReportDisplayerImpl implements ReportDisplayer {

    @Autowired
    private JDA jda;

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoCard daoCard;

    Properties properties = new Properties();

    @Override
    public void sendReportToDiscord(UserData userData, String titleChannel) throws IOException {
        properties.load(new FileInputStream(new File("src\\main\\resources\\titleChannels.properties")));

        List<TextChannel> textChannels = jda.getTextChannelsByName(properties.getProperty(titleChannel), false);
        if (textChannels.size() != 1) {
            System.out.println("Неверное число каналов [sendStatisticsToDiscord]");
        } else {
            TextChannel textChannel = textChannels.get(0);

            Card card = daoCard.getCardByPoints(userData.getCurrentPoints());

           textChannel.getMembers().forEach(member -> {
                if (member.getIdLong() == userData.getUserId()) {
                    textChannel.sendMessage(member.getAsMention()).submit();
                    textChannel.sendMessage(card.getUrl()).submit();
                }
            });


        }


    }

}
