package com.bot.ranksystem_20.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigurationRoles {

    private JDA jda;
    private List<Role> roles;

    public ConfigurationRoles(JDA jda) {
        this.jda = jda;
        roles = jda.getRoles();
    }

    public List<Role> getRoles() {
        return roles;
    }
}
