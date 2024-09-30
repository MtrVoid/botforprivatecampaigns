package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.example.commands.PrivateCampaignsCommand;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainBot {
    private final Dotenv config;
    private final ShardManager shardManager;
    private final ScheduledExecutorService scheduler;

    public MainBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Caballeros Y Dragones"));
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);

        shardManager = builder.build();

        shardManager.addEventListener(
                new PrivateCampaignsCommand()
        );

        shardManager.getShards().forEach(jda -> {
            jda.updateCommands()
                    .addCommands(
                            Commands.slash("crearcanal", "Crea un canal privado con un rol privado.")
                                    .addOptions(
                                            new OptionData(OptionType.STRING, "nombrecanal", "Nombra al canal que quieres crear.", true),
                                            new OptionData(OptionType.STRING, "nombrerol", "Nombra al rol que quieres crear.", true)
                                    )
                    )
                    .queue(
                            success -> System.out.println("Comandos actualizados exitosamente."),
                            error -> System.err.println("Error al actualizar comandos: " + error.getMessage())
                    );
        });

        scheduler = Executors.newScheduledThreadPool(1);
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public void shutdown() {
        shardManager.shutdown();
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        try {
            MainBot bot = new MainBot();
            Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
        } catch (LoginException e) {
            System.err.println("ERROR: Provided bot TOKEN is invalid!");
            e.printStackTrace();
        }
    }
}
