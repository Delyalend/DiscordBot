package com.bot.ranksystem_20.displayReports;

import com.bot.ranksystem_20.model.UserData;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DisplayReport {

    void sendReportToDiscord(UserData userData,String titleChannel) throws IOException;

}
