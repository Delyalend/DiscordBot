package com.bot.ranksystem_20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private Title title;
    private int points;
    private int lastPointsReceived;
    private boolean isNewTitle;

    public boolean hasRank() {
        return title != null;
    }
}