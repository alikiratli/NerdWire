@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
}

@Service
public class BotInitializer {

    private final BotConfig config;

    @Autowired
    public BotInitializer(BotConfig config) {
        this.config = config;
    }

    public void startBot() {
        String token = config.getToken();
        TelegramBot bot = new TelegramBot(token);
        // ...
    }
