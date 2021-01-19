package com.bot.ranksystem_20.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

@Configuration
public class ConfigurationBot {

    @Bean
    public JDA jda() throws LoginException {

        //AuthorizationConfig config = new AuthorizationConfig("Nzc0MTM1MzIzNTE2OTkzNTQ2.X6TXsw.HLHh8OrZR1PsjFgWaD-u5uOSEBM");
        ///return new JDAImpl(config);

        return new JDABuilder("Nzc0MTM1MzIzNTE2OTkzNTQ2.X6TXsw.0FuTkb2riI5k-nIl-EYUCeISZJE")
                .setEnabledIntents(Arrays.asList(GatewayIntent.values().clone()))
                .build();

    }

}