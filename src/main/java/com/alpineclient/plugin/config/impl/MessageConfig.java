package com.alpineclient.plugin.config.impl;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.AbstractConfig;
import com.alpineclient.plugin.config.object.ConfigMessage;
import com.alpineclient.plugin.config.object.MessageType;
import com.alpineclient.plugin.util.Components;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@NoArgsConstructor
public final class MessageConfig extends AbstractConfig<MessageConfig> {

    {
        Components.setMessageConfig(this);
        ConfigMessage.setConfig(this);
    }

    public ConfigMessage prefix = ConfigMessage.builder()
            .message(
                    Component.text("[").color(DIVIDER_COLOR),
                    Component.text(Reference.NAME).color(PRIMARY_COLOR),
                    Component.text("]").color(DIVIDER_COLOR))
            .withoutPrefix()
            .build();

    public ConfigMessage errorPrefix = ConfigMessage.builder()
            .message(
                    Component.text("[").color(DIVIDER_COLOR),
                    Component.text(Reference.NAME).color(PRIMARY_ERROR_COLOR),
                    Component.text("]").color(DIVIDER_COLOR))
            .type(MessageType.ERROR)
            .withoutPrefix()
            .build();

    public ConfigMessage listItem = ConfigMessage.builder()
            .message(
                    Component.text(" - ").color(SECONDARY_COLOR).decorate(TextDecoration.BOLD),
                    Component.text("%value%"))
            .withoutPrefix()
            .build();

    public ConfigMessage mapListItem = ConfigMessage.builder()
            .message(
                    Component.text(" - ").color(SECONDARY_COLOR).decorate(TextDecoration.BOLD),
                    Component.text("%key%").color(PRIMARY_COLOR).decorate(TextDecoration.BOLD),
                    Component.text(" › ").color(DIVIDER_COLOR),
                    Component.text("%value%"))
            .withoutPrefix()
            .build();

    public ConfigMessage clientCheck = ConfigMessage.normal(
            Component.text("%player_name%").color(TEXT_COLOR),
            Component.text("%player_status%"),
            Component.text("using %client_text%.").color(TEXT_COLOR)
    );

    public ConfigMessage notifSuccess = ConfigMessage.normal(
            Component.text("Successfully notified ").color(TEXT_COLOR),
            Component.text("%player_name%").color(SECONDARY_COLOR),
            Component.text(".").color(TEXT_COLOR)
    );

    public ConfigMessage notOnClient = ConfigMessage.error(
            Component.text("%player_name%").color(SECONDARY_ERROR_COLOR),
            Component.text(" is not on Alpine Client.").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage listPlayers = ConfigMessage.builder()
            .message(
                    Components.joinNewLines(
                            Component.text("----------------------------------------").color(DARK_DIVIDER_COLOR).decorate(TextDecoration.BOLD, TextDecoration.STRIKETHROUGH),
                            Component.empty().append(
                                    Component.text("Connected (%player_amount%): ").color(PRIMARY_COLOR).decorate(TextDecoration.BOLD)
                            ).append(
                                    Component.text("%player_names%").color(TEXT_COLOR)
                            ),
                            Component.text("----------------------------------------").color(DARK_DIVIDER_COLOR).decorate(TextDecoration.BOLD, TextDecoration.STRIKETHROUGH)
                    )
            )
            .withoutPrefix()
            .build();

    public MessageConfig(@NotNull Path configPath) {
        super(configPath);
    }
}