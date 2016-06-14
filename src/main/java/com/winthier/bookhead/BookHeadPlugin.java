package com.winthier.bookhead;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player)sender : null;
        if (player == null) return false;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.WRITTEN_BOOK) return false;
        BookMeta meta = (BookMeta)item.getItemMeta();
        if (meta.getPageCount() != 1) return false;
        String page = meta.getPage(1);
        if (!page.matches("[a-zA-Z0-9=]+")) return false;
        String name = player.getName();
        String cmd = String.format("give %s skull 1 3 {display:{Name:\"%s\"},SkullOwner:{Id:\"%s\",Properties:{textures:[{Value:\"%s\"}]}}}", name, name, UUID.randomUUID().toString(), page);
        getLogger().info("Running command: " + cmd);
        getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
        player.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "BookHead" + ChatColor.RESET + ChatColor.ITALIC + " Enjoy your custom player head!");
        return true;
    }
}
