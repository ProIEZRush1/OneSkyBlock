package me.proiezrush.oneskyblock.island;

import me.proiezrush.oneskyblock.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Island {

    private final Main m;
    private final Location spawn;
    private final IslandPlayer owner;
    private final List<IslandPlayer> players;
    private boolean locked;
    private Location ob;
    private final List<IslandPlayer> addedPlayers;
    public Island(Main m, Location spawn, IslandPlayer owner) {
        this.m = m;
        this.spawn = spawn;
        this.owner = owner;
        this.players = new ArrayList<>();
        this.locked = false;
        this.ob = null;
        this.addedPlayers = new ArrayList<>();
    }

    public void addPlayer(IslandPlayer player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(IslandPlayer player) {
        players.remove(player);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setOb(Location ob) {
        this.ob = ob;
    }

    public void addAddedPlayer(IslandPlayer player) {
        if (!addedPlayers.contains(player)) {
            addedPlayers.add(player);
        }
    }

    public void removeAddedPlayer(IslandPlayer player) {
        addedPlayers.remove(player);
    }

    public Location getSpawn() {
        return spawn;
    }

    public IslandPlayer getOwner() {
        return owner;
    }

    public boolean containsPlayer(IslandPlayer player) {
        return players.contains(player);
    }

    public List<IslandPlayer> getPlayers() {
        return players;
    }

    public boolean isLocked() {
        return locked;
    }

    public Location getOb() {
        return ob;
    }

    public boolean containsAddedPlayer(Player player) {
        return addedPlayers.contains(m.getPlayer(player));
    }

    public List<IslandPlayer> getAddedPlayers() {
        return addedPlayers;
    }
}
