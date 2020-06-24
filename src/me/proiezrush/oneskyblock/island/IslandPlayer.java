package me.proiezrush.oneskyblock.island;

import me.proiezrush.oneskyblock.Main;
import me.proiezrush.oneskyblock.island.menus.*;
import org.bukkit.entity.Player;

public class IslandPlayer implements Comparable<IslandPlayer> {

    private final Main m;
    private final Player player;
    private Island currentIsland;
    private IsMenu isMenu;
    private TopMenu topMenu;
    private KickPlayerMenu kickPlayerMenu;
    private AddPlayerMenu addPlayerMenu;
    private TradingsMenu tradingsMenu;
    private int resetsLeft;
    private int level;
    private int blocksMined;
    private boolean islandChat;
    public IslandPlayer(Main m, Player player) {
        this.m = m;
        this.player = player;
        this.currentIsland = null;
        this.isMenu = new IsMenu(m, this);
        this.topMenu = new TopMenu(m, this);
        this.kickPlayerMenu = new KickPlayerMenu(m, this);
        this.addPlayerMenu = new AddPlayerMenu(m, this);
        this.tradingsMenu = new TradingsMenu(m, this);
        this.resetsLeft = m.getC().getInt("MaxResets");
        this.level = 0;
        this.blocksMined = 0;
        this.islandChat = false;
    }

    public void setCurrentIsland(Island currentIsland) {
        this.currentIsland = currentIsland;
    }

    public void addReset() {
        resetsLeft++;
    }

    public void removeReset() {
        resetsLeft--;
    }

    public void addLevel() {
        level++;
    }

    public void removeLevel() {
        level--;
    }

    public void addBlock() {
        blocksMined++;
    }

    public void removeBlock() {
        blocksMined--;
    }

    public void setIslandChat(boolean islandChat) {
        this.islandChat = islandChat;
    }

    public Player getPlayer() {
        return player;
    }

    public Island getCurrentIsland() {
        return currentIsland;
    }

    public IsMenu getIsMenu() {
        isMenu = new IsMenu(m, this);
        return isMenu;
    }

    public TopMenu getTopMenu() {
        topMenu = new TopMenu(m, this);
        return topMenu;
    }

    public KickPlayerMenu getKickPlayerMenu() {
        kickPlayerMenu = new KickPlayerMenu(m, this);
        return kickPlayerMenu;
    }

    public AddPlayerMenu getAddPlayerMenu() {
        addPlayerMenu = new AddPlayerMenu(m, this);
        return addPlayerMenu;
    }

    public TradingsMenu getTradingsMenu() {
        tradingsMenu = new TradingsMenu(m, this);
        return tradingsMenu;
    }

    public int getResetsLeft() {
        return resetsLeft;
    }

    public int getLevel() {
        return level;
    }

    public int getBlocksMined() {
        return blocksMined;
    }

    public boolean isIslandChat() {
        return islandChat;
    }

    public boolean hasResetsLeft() {
        return resetsLeft != 0;
    }

    public boolean isInIsland() {
        return player.getWorld().getName().equals(player.getName() + "_OB");
    }

    @Override
    public int compareTo(IslandPlayer o) {
        int level = o.getLevel();
        return this.level - level;
    }
}
