package me.proiezrush.oneskyblock;

import me.proiezrush.oneskyblock.commands.ObCommand;
import me.proiezrush.oneskyblock.commands.TradeCommand;
import me.proiezrush.oneskyblock.island.Island;
import me.proiezrush.oneskyblock.island.IslandPlayer;
import me.proiezrush.oneskyblock.island.menus.Menu;
import me.proiezrush.oneskyblock.island.menus.TradingInvMenu;
import me.proiezrush.oneskyblock.listeners.BlockListeners;
import me.proiezrush.oneskyblock.listeners.ClickListeners;
import me.proiezrush.oneskyblock.listeners.PlayerListeners;
import me.proiezrush.oneskyblock.utils.ItemBuilder;
import me.proiezrush.oneskyblock.utils.Settings;
import me.proiezrush.oneskyblock.utils.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class Main extends JavaPlugin {

    private Settings config, messages, blocks;
    private Admin adm;
    private Worlds worlds;
    private HashMap<Player, IslandPlayer> islandPlayers;
    private HashMap<IslandPlayer, Island> islands;
    private HashMap<Integer, Menu> tradingInventories;

    @Override
    public void onEnable() {
        File f = new File("plugins/OneSkyBlock");
        if (!f.exists()) {
            f.mkdir();
        }
        this.config = new Settings(this, "config", false, true);
        this.messages = new Settings(this, "messages", false, true);
        this.blocks = new Settings(this, "blocks", false, true);
        this.adm = new Admin();
        this.worlds = new Worlds();
        this.islandPlayers = new HashMap<>();
        this.islands = new HashMap<>();
        this.tradingInventories = new HashMap<>();
        loadTradingInventories();

        this.getCommand("ob").setExecutor(new ObCommand(this));
        this.getCommand("trade").setExecutor(new TradeCommand(this));
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockListeners(this), this);
    }

    @Override
    public void onDisable() {
        islandPlayers.clear();
        islands.clear();
    }

    public void addIslandPlayer(IslandPlayer player) {
        islandPlayers.putIfAbsent(player.getPlayer(), player);
    }

    public void removeIslandPlayer(Player player) {
        islandPlayers.remove(player);
    }

    public void addIsland(Island island) {
        islands.putIfAbsent(island.getOwner(), island);
    }

    public void removeIsland(IslandPlayer player) {
        islands.remove(player);
    }

    public void addTradingInv(int ID, Menu menu) {
        tradingInventories.putIfAbsent(ID, menu);
    }

    public void removeTradingInv(int ID) {
        tradingInventories.remove(ID);
    }

    public Settings getC() {
        return config;
    }

    public Settings getMessages() {
        return messages;
    }

    public Settings getBlocks() {
        return blocks;
    }

    public Admin getAdm() {
        return adm;
    }

    public Worlds getWorlds() {
        return worlds;
    }

    public HashMap<Player, IslandPlayer> getIslandPlayers() {
        return islandPlayers;
    }

    public HashMap<IslandPlayer, Island> getIslands() {
        return islands;
    }

    public HashMap<Integer, Menu> getTradingInventories() {
        return tradingInventories;
    }

    public boolean hasIsland(IslandPlayer player) {
        for (Island island : islands.values()) {
            if (island.getOwner().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public IslandPlayer getPlayer(Player p) {
        if (islandPlayers.get(p) != null) {
            return islandPlayers.get(p);
        }
        return null;
    }

    public Island getIsland(IslandPlayer player) {
        if (hasIsland(player)) {
            return islands.get(player);
        }
        return null;
    }

    public Menu getTradingInv(int ID) {
        if (tradingInventories.get(ID) != null) {
            return tradingInventories.get(ID);
        }
        return null;
    }

    public int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void loadTradingInventories() {
        String PATH = "TradingInventories";
        ConfigurationSection a = config.getConfig().getConfigurationSection(PATH);
        if (a != null) {
            for (String k : a.getKeys(false)) {
                String name = a.getString(k + ".Name");
                int size = a.getInt(k + ".Size");

                TradingInvMenu tradingInvMenu = new TradingInvMenu(this, name, size);

                ConfigurationSection b = a.getConfigurationSection(k + ".Items");
                for (String j : b.getKeys(false)) {
                    String iName = b.getString(j + ".Name");
                    List<String> lore = b.getStringList(j + ".Lore");
                    String material = b.getString(j + ".Material");
                    int data = b.getInt(j + ".Data");
                    int slot = b.getInt(j + ".Slot");

                    ItemBuilder item = new ItemBuilder(this, iName, material, data, true, lore);

                    tradingInvMenu.getInventory().addItem(slot, item.build());
                }
                addTradingInv(Integer.parseInt(k), tradingInvMenu);
            }
        }
    }
}
