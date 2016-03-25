package com.winthier.bookhead;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BookHeadPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        if (!event.isSigning()) return;
        if (!event.getPlayer().hasPermission("bookhead.create")) return;
        BookMeta meta = event.getNewBookMeta();
        if (!meta.hasTitle() || !meta.getTitle().equalsIgnoreCase("head")) return;
        if (meta.getPageCount() != 1) return;
        String page = meta.getPage(1);
        if (!page.matches("[a-zA-Z0-9=]+")) return;
        String name = event.getPlayer().getName();
        String cmd = String.format("give %s skull 1 3 {display:{Name:\"%s\"},SkullOwner:{Id:\"%s\",Properties:{textures:[{Value:\"%s\"}]}}}", name, name, UUID.randomUUID().toString(), page);
        getLogger().info("Running command: " + cmd);
        getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
        event.getPlayer().sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "BookHead" + ChatColor.RESET + ChatColor.ITALIC + " Enjoy your custom player head!");
        event.setCancelled(true);
    }
}
