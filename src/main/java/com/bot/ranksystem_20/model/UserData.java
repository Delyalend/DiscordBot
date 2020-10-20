package com.bot.ranksystem_20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private Long userId;
    private int lastPointsReceived;
    private int currentPoints;
    private int pointsForNextTitle;
    private Title currentTitle;
    private Title nextTitle;
    private boolean isNewTitle = false;

}
