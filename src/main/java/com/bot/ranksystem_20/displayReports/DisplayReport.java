package com.bot.ranksystem_20.displayReports;

import com.bot.ranksystem_20.model.UserData;

public interface DisplayReport {

    void sendReportToDiscord(UserData userData,String titleChannel);

}
