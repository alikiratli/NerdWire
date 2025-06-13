package org.nerdwire.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
}
