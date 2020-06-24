package me.proiezrush.oneskyblock.island;

import me.proiezrush.oneskyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IslandManager {

    public static void resetIsland(Main m, IslandPlayer owner, Island island) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (owner.hasResetsLeft()) {
                p.teleport(new Location(Bukkit.getWorld("world"), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()));
                m.getWorlds().deleteWorld(island.getSpawn().getWorld().getName(), island.getSpawn().getWorld().getWorldFolder());
                m.removeIsland(owner);
                owner.removeReset();
                p.sendMessage(m.getMessages().get(p, "island-reset"));
            } else p.sendMessage(m.getMessages().get(p, "no-resets"));
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void kickPlayer(Main m, IslandPlayer owner, Island island, IslandPlayer player) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (island.containsPlayer(player)) {
                island.removePlayer(player);
                if (m.getIsland(player) != null) {
                    player.getPlayer().teleport(m.getIsland(player).getSpawn());
                } else
                    p.teleport(new Location(Bukkit.getWorld("world"), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()));
                island.removeAddedPlayer(player);
                p.sendMessage(m.getMessages().get(p, "player-kicked"));
            } else p.sendMessage(m.getMessages().get(p, "player-not-in-island"));
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void lockIsland(Main m, IslandPlayer owner, Island island) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (!island.isLocked()) {
                island.setLocked(true);
                p.sendMessage(m.getMessages().get(p, "island-locked"));
            } else {
                island.setLocked(false);
                p.sendMessage(m.getMessages().get(p, "island-unlocked"));
            }
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void addPlayer(Main m, IslandPlayer owner, Island island, IslandPlayer player) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (!island.containsPlayer(player)) {
                island.addAddedPlayer(player);
                player.getPlayer().sendMessage(m.getMessages().get(player.getPlayer(), "added-to-island").replace("%player%", p.getName()));
                p.sendMessage(m.getMessages().get(p, "player-added"));
            }
            else p.sendMessage(m.getMessages().get(p, "player-already-in-island"));
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void islandLevel(Main m, IslandPlayer owner) {
        Player p = owner.getPlayer();
        p.sendMessage(m.getMessages().get(p, "island-level").replace("%level%", String.valueOf(owner.getLevel())));
        p.closeInventory();
    }

    public static void teleport(Main m, IslandPlayer player, Island island) {
        Player p = player.getPlayer();
        if (island != null) {
            if (!island.isLocked() || island.containsAddedPlayer(p)) {
                island.addPlayer(player);
                p.teleport(island.getSpawn());
                p.sendMessage(m.getMessages().get(p, "teleported-island").replace("%player%", island.getOwner().getPlayer().getName()));
            }
            else p.sendMessage(m.getMessages().get(p, "other-island-locked"));
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void spawn(Main m, IslandPlayer owner, Island island) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (island.getOwner().equals(owner)) {
                p.teleport(island.getSpawn());
                p.sendMessage(m.getMessages().get(p, "teleport-spawn"));
            }
            else p.sendMessage(m.getMessages().get(p, "not-owner"));
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

    public static void islandChat(Main m, IslandPlayer owner, Island island) {
        Player p = owner.getPlayer();
        if (island != null) {
            if (owner.isIslandChat()) {
                owner.setIslandChat(false);
                p.sendMessage(m.getMessages().get(p, "island-chat-enabled"));
            }
            else {
                owner.setIslandChat(true);
                p.sendMessage(m.getMessages().get(p, "island-chat-disabled"));
            }
        }
        else p.sendMessage(m.getMessages().get(p, "island-not-exist"));
        p.closeInventory();
    }

}
