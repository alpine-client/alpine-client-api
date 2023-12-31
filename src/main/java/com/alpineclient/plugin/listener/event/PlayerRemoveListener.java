package com.alpineclient.plugin.listener.event;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.event.ClientDisconnectEvent;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.framework.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class PlayerRemoveListener extends EventListener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (Plugin.getInstance().getPlayerHandler().isPlayerConnected(id)) {
            AlpinePlayer player = Plugin.getInstance().getPlayerHandler().getConnectedPlayer(id);
            Plugin.getInstance().getPlayerHandler().removeConnectedPlayer(id);
            Bukkit.getPluginManager().callEvent(new ClientDisconnectEvent(player));
        }
    }
}
