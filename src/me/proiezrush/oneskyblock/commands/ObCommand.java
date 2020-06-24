package me.proiezrush.oneskyblock.commands;

import me.proiezrush.oneskyblock.Main;
import me.proiezrush.oneskyblock.island.Island;
import me.proiezrush.oneskyblock.island.IslandPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ObCommand implements CommandExecutor {

    private final Main m;
    public ObCommand(Main m) {
        this.m = m;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("ob")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("ob.command")) {
                    IslandPlayer player = m.getPlayer(p);
                    if (args.length == 0) {
                        if (!m.hasIsland(player)) {
                            m.getWorlds().createVoidWorld(p.getName() + "_OB");
                            Island island = new Island(m, new Location(Bukkit.getWorld(p.getName() + "_OB"), 0.5D, 75D, 0.5D), player);
                            island.addPlayer(player);
                            island.getSpawn().getBlock().setType(Material.GLASS);
                            island.setOb(island.getSpawn());
                            m.addIsland(island);
                            player.setCurrentIsland(island);
                            p.teleport(island.getSpawn().add(new Vector(0D, 1D, 0D)));
                            p.sendMessage(m.getMessages().get(p, "island-created"));
                            return true;
                        }
                        p.openInventory(player.getIsMenu().getInventory().getInventory());
                    }
                }
                else p.sendMessage(m.getMessages().get(p, "no-permission"));
            }
            else commandSender.sendMessage(m.getMessages().get(null, "console-error"));
        }
        return false;
    }
}
