package com.bot.ranksystem_20.configuration;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBot {

    @SneakyThrows
    @Bean
    public JDA jda() {
        return new JDABuilder("NzM4Njk4MjgzMzAzNTY3NDYy.XyPsYw.aLR5ETvn-YrQIYjk1jj_odP1Z3o")
                .build();
    }

}