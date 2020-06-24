package me.proiezrush.oneskyblock.listeners;

import me.proiezrush.oneskyblock.Main;
import me.proiezrush.oneskyblock.island.IslandPlayer;
import me.proiezrush.oneskyblock.utils.Scoreboard;
import me.proiezrush.oneskyblock.utils.TabList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListeners implements Listener {

    private final Main m;
    public PlayerListeners(Main m) {
        this.m = m;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TabList tabList = new TabList(getHeader(), getFooter());
        tabList.send(p);
        Scoreboard scoreboard = new Scoreboard(m, m.getC().get(p, "Scoreboard.Title"), m.getC().getList("Scoreboard.List"));
        scoreboard.set();
        IslandPlayer islandPlayer = new IslandPlayer(m, p);
        m.addIslandPlayer(islandPlayer);
        //Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> p.openInventory(m.getTradingInv(1).getInventory().getInventory()), 5);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        m.removeIslandPlayer(p);
    }

    private String getHeader() {
        List<String> list = m.getC().getList("TabList.Header");
        StringBuilder a = new StringBuilder();
        for (String s : list) {
            a.append("\n").append(s);
        }
        return a.toString();
    }

    private String getFooter() {
        List<String> list = m.getC().getList("TabList.Footer");
        StringBuilder a = new StringBuilder();
        for (String s : list) {
            a.append("\n").append(s);
        }
        return a.toString();
    }

}
