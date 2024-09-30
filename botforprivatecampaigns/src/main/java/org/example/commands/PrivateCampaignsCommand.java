package org.example.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateCampaignsCommand extends ListenerAdapter {

    private final Dotenv dotenv;

    public PrivateCampaignsCommand() {
        this.dotenv = Dotenv.load();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("crearcanal")) {
            String channelName = event.getOption("nombrecanal").getAsString();
            String roleName = event.getOption("nombrerol").getAsString();

            Guild guild = event.getGuild();
            if (guild == null) {
                event.reply("Este comando solo se puede ejecutar en un servidor.").queue();
                return;
            }

            String categoryId = dotenv.get("CATEGORY_ID");

            Category category = guild.getCategoryById(categoryId);
            if (category == null) {
                event.reply("Categoría no encontrada").queue();
                return;
            }

            guild.createRole().setName(roleName).queue(role -> {
                category.createTextChannel(channelName).queue(textChannel -> {
                    textChannel.upsertPermissionOverride(guild.getPublicRole())
                            .deny(Permission.VIEW_CHANNEL)
                            .queue();

                    textChannel.upsertPermissionOverride(role)
                            .grant(Permission.VIEW_CHANNEL)
                            .queue();

                    event.reply("Canal privado creado: " + textChannel.getAsMention() +
                            " con acceso para el rol: <@&" + role.getId() + ">").queue();
                });
            });
        }
    }
}
