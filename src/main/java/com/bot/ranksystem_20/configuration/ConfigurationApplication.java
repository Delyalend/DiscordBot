package com.bot.ranksystem_20.configuration;

import com.bot.ranksystem_20.core.RankSystem;
import com.bot.ranksystem_20.core.RankSystemImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationApplication {

    @Bean
    public RankSystem rankSystem() {
        return new RankSystemImpl();
    }

}
