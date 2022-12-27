package com.adriancasares.foursquare.base.util.chat;

import com.adriancasares.foursquare.base.Game;
import com.adriancasares.foursquare.base.util.FSColor;
import net.kyori.adventure.text.Component;

public class MessageTemplate {

    private Component message = Component.empty();
    private FSColor color;

    public MessageTemplate(FSColor color) {
        this.color = color;
    }

    public MessageTemplate append(Component component) {
        message = message.append(component);
        return this;
    }

    public MessageTemplate appendImportant(String string) {
        message = message.append(Component.text(string).color(color.getTextAccent()));
        return this;
    }

    public MessageTemplate appendText(String string) {
        message = message.append(Component.text(string).color(color.getTextColor()));
        return this;
    }

    public Component getMessage() {
        return message;
    }

    public void sendTo(Game game) {
        game.getTeam().getPlayers().forEach((player) -> {
            if(player.getPlayer() != null) {
                player.getPlayer().sendMessage(message);
            }
        });

        game.getTeam().getSpectators().forEach((player) -> {
            if(player.getPlayer() != null) {
                player.getPlayer().sendMessage(message);
            }
        });
    }
}
