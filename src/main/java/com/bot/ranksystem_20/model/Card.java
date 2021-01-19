package com.bot.ranksystem_20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    private Long id;
    private int points;
    private String url;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", points=" + points +
                ", url='" + url + '\'' +
                '}';
    }
}
